/**
 * Account Management — Frontend Logic
 * Kết nối với backend API /api/v1/users (chỉ ADMIN mới truy cập được)
 *
 * Chức năng:
 *  - Load danh sách tài khoản từ DB (FR-07)
 *  - Thêm tài khoản mới (FR-02)
 *  - Sửa tài khoản (FR-03)
 *  - Khóa/Mở khóa tài khoản (FR-04)
 *  - Đặt lại mật khẩu (FR-09)
 *  - Phân quyền (FR-06)
 */
(() => {
  const API_BASE = 'http://localhost:8080';

  // ─── AUTH HELPERS ─────────────────────────────────────────
  function getToken() {
    return localStorage.getItem('jwt_token') || '';
  }

  function getAuthHeaders() {
    const headers = { 'Content-Type': 'application/json' };
    const token = getToken();
    if (token) headers['Authorization'] = 'Bearer ' + token;
    return headers;
  }

  // Map role tiếng Việt ↔ backend enum
  const ROLE_MAP = {
    'ADMIN': 'Admin',
    'TEACHER': 'Giáo viên',
    'OFFICE_STAFF': 'Giáo vụ',
    'PRINCIPAL': 'BGH',
    'STUDENT': 'Học sinh'
  };

  const ROLE_CSS = {
    'ADMIN': 'role-admin',
    'TEACHER': 'role-teacher',
    'OFFICE_STAFF': 'role-office',
    'PRINCIPAL': 'role-bgh',
    'STUDENT': 'role-student'
  };

  function roleToVietnamese(role) {
    return ROLE_MAP[role] || role;
  }

  function roleToCss(role) {
    return ROLE_CSS[role] || 'role-teacher';
  }

  // ─── DISPLAY USER INFO ────────────────────────────────────
  const userDisplayName = document.getElementById('userDisplayName');
  const userAvatar = document.getElementById('userAvatar');
  const fullName = localStorage.getItem('full_name');
  if (fullName && userDisplayName) {
    userDisplayName.textContent = fullName;
    if (userAvatar) userAvatar.textContent = fullName.substring(0, 2).toUpperCase();
  }

  // ─── MODAL HELPERS ────────────────────────────────────────
  const editAccountModal = document.getElementById('editAccountModal');
  const addAccountModal = document.getElementById('addAccountModal');

  function openModal(modal) {
    modal.hidden = false;
    document.body.classList.add('modal-open');
  }

  function closeModal(modal) {
    modal.hidden = true;
    if (editAccountModal.hidden && addAccountModal.hidden) {
      document.body.classList.remove('modal-open');
    }
  }

  // Close buttons
  document.querySelectorAll('#editAccountModal .modal-close-btn, #editAccountModal .modal-cancel-btn')
    .forEach(btn => btn.addEventListener('click', () => closeModal(editAccountModal)));
  document.querySelectorAll('#addAccountModal .modal-close-btn, #addAccountModal .add-modal-cancel-btn')
    .forEach(btn => btn.addEventListener('click', () => closeModal(addAccountModal)));

  // Click backdrop
  editAccountModal.addEventListener('click', e => { if (e.target === editAccountModal) closeModal(editAccountModal); });
  addAccountModal.addEventListener('click', e => { if (e.target === addAccountModal) closeModal(addAccountModal); });

  // Esc key
  document.addEventListener('keydown', e => {
    if (e.key === 'Escape') {
      if (!editAccountModal.hidden) closeModal(editAccountModal);
      if (!addAccountModal.hidden) closeModal(addAccountModal);
    }
  });

  // Open add modal buttons
  document.querySelectorAll('.open-add-account-btn')
    .forEach(btn => btn.addEventListener('click', () => {
      document.getElementById('addAccountForm').reset();
      document.getElementById('addAccountMsg').style.display = 'none';
      openModal(addAccountModal);
      document.getElementById('addAccountFullName').focus();
    }));

  // ─── LOAD ACCOUNTS FROM API ────────────────────────────────
  let allUsers = [];

  async function loadAccounts() {
    const tbody = document.getElementById('accountTableBody');
    if (!tbody) return;

    tbody.innerHTML = '<tr><td colspan="6" style="text-align:center">Đang tải...</td></tr>';

    try {
      const response = await fetch(`${API_BASE}/api/v1/users?page=0&size=100`, {
        method: 'GET',
        headers: getAuthHeaders()
      });

      if (response.status === 401) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align:center;color:#e74c3c">Phiên đăng nhập hết hạn. <a href="Login.html">Đăng nhập lại</a></td></tr>';
        return;
      }
      if (response.status === 403) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align:center;color:#e74c3c">Bạn không có quyền truy cập chức năng này!</td></tr>';
        return;
      }

      const result = await response.json();
      let users = [];

      if (result && result.data && Array.isArray(result.data.content)) {
        users = result.data.content;
      } else if (result && Array.isArray(result.data)) {
        users = result.data;
      }

      allUsers = users;
      updateStats(users);
      renderTable(users, tbody);

    } catch (error) {
      console.error('Lỗi tải danh sách tài khoản:', error);
      tbody.innerHTML = '<tr><td colspan="6" style="text-align:center">Lỗi kết nối đến máy chủ</td></tr>';
    }
  }

  function updateStats(users) {
    const total = users.length;
    const activeCount = users.filter(u => u.status === 'active').length;
    const lockedCount = users.filter(u => u.status === 'locked').length;
    const adminCount = users.filter(u => u.role === 'ADMIN').length;
    const teacherStaffCount = users.filter(u => ['TEACHER', 'OFFICE_STAFF'].includes(u.role)).length;

    const statTotal = document.getElementById('statTotal');
    const statActive = document.getElementById('statActive');
    const statLocked = document.getElementById('statLocked');
    const statAdminCount = document.getElementById('statAdminCount');
    const statTeacherCount = document.getElementById('statTeacherCount');

    if (statTotal) statTotal.textContent = total;
    if (statActive) statActive.textContent = activeCount;
    if (statLocked) statLocked.textContent = lockedCount < 10 ? '0' + lockedCount : lockedCount;
    if (statAdminCount) statAdminCount.textContent = adminCount < 10 ? '0' + adminCount : adminCount;
    if (statTeacherCount) statTeacherCount.textContent = teacherStaffCount;
  }

  function renderTable(users, tbody) {
    if (users.length === 0) {
      tbody.innerHTML = '<tr><td colspan="6" style="text-align:center">Không có tài khoản nào</td></tr>';
      return;
    }

    tbody.innerHTML = '';
    users.forEach(user => {
      const tr = document.createElement('tr');
      const isActive = user.status === 'active';
      const statusCss = isActive ? 'status--success' : 'status--danger';
      const statusText = isActive ? 'Hoạt động' : 'Bị khóa';
      const toggleText = isActive ? 'Khóa' : 'Mở khóa';

      tr.innerHTML = `
        <td>${user.userId}</td>
        <td>${user.fullName || user.username}</td>
        <td>${user.email}</td>
        <td><span class="role-badge ${roleToCss(user.role)}">${roleToVietnamese(user.role)}</span></td>
        <td><span class="status ${statusCss}">${statusText}</span></td>
        <td>
          <div class="action-group">
            <button class="table-btn btn-edit-account"
              data-user-id="${user.userId}"
              data-full-name="${user.fullName || ''}"
              data-email="${user.email}"
              data-role="${user.role}"
              type="button">Sửa</button>
            <button class="table-btn btn-toggle-status"
              data-user-id="${user.userId}"
              type="button">${toggleText}</button>
            <button class="table-btn btn-reset-pw"
              data-user-id="${user.userId}"
              type="button">Reset MK</button>
          </div>
        </td>
      `;
      tbody.appendChild(tr);
    });

    // Bind event handlers
    document.querySelectorAll('.btn-edit-account').forEach(btn => {
      btn.addEventListener('click', () => openEditModal(btn));
    });
    document.querySelectorAll('.btn-toggle-status').forEach(btn => {
      btn.addEventListener('click', () => toggleAccountStatus(btn.dataset.userId));
    });
    document.querySelectorAll('.btn-reset-pw').forEach(btn => {
      btn.addEventListener('click', () => resetPassword(btn.dataset.userId));
    });
  }

  // ─── ADD ACCOUNT (FR-02) ──────────────────────────────────
  const addAccountForm = document.getElementById('addAccountForm');

  addAccountForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const msgEl = document.getElementById('addAccountMsg');
    msgEl.style.display = 'none';

    const fullNameVal = document.getElementById('addAccountFullName').value.trim();
    const usernameVal = document.getElementById('addAccountUsername').value.trim();
    const emailVal = document.getElementById('addAccountEmail').value.trim();
    const passwordVal = document.getElementById('addAccountPassword').value;
    const roleVal = document.getElementById('addAccountRole').value;

    if (!fullNameVal || !usernameVal || !emailVal || !roleVal || roleVal === '') {
      showMsg(msgEl, 'Vui lòng nhập đầy đủ thông tin!', true);
      return;
    }

    const payload = {
      fullName: fullNameVal,
      username: usernameVal,
      email: emailVal,
      password: passwordVal || null,
      role: roleVal
    };

    try {
      const response = await fetch(`${API_BASE}/api/v1/users`, {
        method: 'POST',
        headers: getAuthHeaders(),
        body: JSON.stringify(payload)
      });

      const result = await response.json();

      if (response.ok && result.success !== false) {
        showMsg(msgEl, result.message || 'Tạo tài khoản thành công!', false);
        setTimeout(() => {
          addAccountForm.reset();
          closeModal(addAccountModal);
          msgEl.style.display = 'none';
          loadAccounts();
        }, 1000);
      } else {
        showMsg(msgEl, result.message || 'Tạo tài khoản thất bại!', true);
      }
    } catch (error) {
      showMsg(msgEl, 'Không thể kết nối máy chủ!', true);
    }
  });

  // ─── EDIT ACCOUNT (FR-03) ─────────────────────────────────
  let editingUserId = null;

  function openEditModal(btn) {
    editingUserId = btn.dataset.userId;
    document.getElementById('editAccountName').value = btn.dataset.fullName || '';
    document.getElementById('editAccountEmail').value = btn.dataset.email || '';
    document.getElementById('editAccountRole').value = btn.dataset.role || '';
    document.getElementById('editAccountPassword').value = '';
    document.getElementById('editAccountMeta').textContent =
      `ID: ${editingUserId} • ${btn.dataset.email}`;
    document.getElementById('editAccountMsg').style.display = 'none';
    openModal(editAccountModal);
    document.getElementById('editAccountName').focus();
  }

  const editAccountForm = document.querySelector('#editAccountModal .modal-form');
  editAccountForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    if (!editingUserId) return;
    const msgEl = document.getElementById('editAccountMsg');
    msgEl.style.display = 'none';

    const payload = {
      fullName: document.getElementById('editAccountName').value.trim() || null,
      email: document.getElementById('editAccountEmail').value.trim() || null,
      role: document.getElementById('editAccountRole').value || null,
      password: document.getElementById('editAccountPassword').value || null
    };

    try {
      const response = await fetch(`${API_BASE}/api/v1/users/${editingUserId}`, {
        method: 'PUT',
        headers: getAuthHeaders(),
        body: JSON.stringify(payload)
      });

      const result = await response.json();

      if (response.ok && result.success !== false) {
        showMsg(msgEl, 'Cập nhật thành công!', false);
        setTimeout(() => {
          closeModal(editAccountModal);
          msgEl.style.display = 'none';
          loadAccounts();
        }, 800);
      } else {
        showMsg(msgEl, result.message || 'Cập nhật thất bại!', true);
      }
    } catch (error) {
      showMsg(msgEl, 'Không thể kết nối máy chủ!', true);
    }
  });

  // ─── TOGGLE STATUS (FR-04) ────────────────────────────────
  async function toggleAccountStatus(userId) {
    if (!confirm('Bạn có chắc muốn thay đổi trạng thái tài khoản này?')) return;

    try {
      const response = await fetch(`${API_BASE}/api/v1/users/${userId}/status`, {
        method: 'PUT',
        headers: getAuthHeaders()
      });
      const result = await response.json();
      alert(result.message || 'Đã cập nhật trạng thái!');
      loadAccounts();
    } catch (error) {
      alert('Không thể kết nối máy chủ!');
    }
  }

  // ─── RESET PASSWORD (FR-09) ───────────────────────────────
  async function resetPassword(userId) {
    if (!confirm('Đặt lại mật khẩu cho tài khoản này?')) return;

    try {
      const response = await fetch(`${API_BASE}/api/v1/users/${userId}/reset-password`, {
        method: 'PUT',
        headers: getAuthHeaders()
      });
      const result = await response.json();

      if (response.ok && result.data) {
        alert(`Mật khẩu mới: ${result.data}\n\nVui lòng ghi lại và gửi cho người dùng.`);
      } else {
        alert(result.message || 'Đặt lại mật khẩu thất bại!');
      }
    } catch (error) {
      alert('Không thể kết nối máy chủ!');
    }
  }

  // ─── HELPER ───────────────────────────────────────────────
  function showMsg(el, msg, isError) {
    el.style.display = 'block';
    el.textContent = msg;
    el.style.color = isError ? '#e74c3c' : '#27ae60';
    el.style.background = isError ? '#fdf0ef' : '#eafaf1';
  }

  // ─── INIT ─────────────────────────────────────────────────
  loadAccounts();

})();
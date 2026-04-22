/**
 * session-manager.js – Quản lý phiên làm việc (NFR-05 / FR-01)
 *
 * Chức năng:
 *  - Theo dõi hoạt động của user (mouse, keyboard, click, scroll, touch)
 *  - Sau 13 phút không có hoạt động → hiện hộp thoại cảnh báo đếm ngược 2 phút
 *  - Sau 15 phút không có hoạt động → tự động đăng xuất, xóa localStorage, redirect Login.html
 *  - Nhấn "Tiếp tục làm việc" → reset timer về 15 phút
 *  - Hiển thị đồng hồ đếm ngược thời gian còn lại trên topbar
 */

(function SessionManager() {
  // ─── CẤU HÌNH ─────────────────────────────────────────────────────────────
  const TOTAL_MS = 15 * 60 * 1000;   // 15 phút
  const WARN_BEFORE = 2 * 60 * 1000;   // Cảnh báo trước 2 phút
  const WARNING_MS = TOTAL_MS - WARN_BEFORE; // 13 phút
  const POLL_MS = 1000;             // Cập nhật mỗi giây

  const LOGIN_PAGE = '../HTML/Login.html';

  // ─── TRẠNG THÁI ───────────────────────────────────────────────────────────
  let lastActivity = Date.now();
  let warnShown = false;
  let tickInterval = null;
  let warnInterval = null;  // đếm ngược trong modal
  let warnSecondsLeft = 0;

  // ─── KIỂM TRA: chỉ chạy nếu đã đăng nhập ─────────────────────────────────
  const token = localStorage.getItem('jwt_token');
  if (!token) return;   // chưa login → không cần quản lý phiên

  // ─── TẠO OVERLAY CẢNH BÁO ─────────────────────────────────────────────────
  injectStyles();
  const overlay = createWarningOverlay();
  document.body.appendChild(overlay);

  const cdDisplay = document.getElementById('sm-countdown-display');
  const btnExtend = document.getElementById('sm-btn-extend');
  const btnLogout = document.getElementById('sm-btn-logout');
  const timerBadge = createTimerBadge();

  if (btnExtend) btnExtend.addEventListener('click', extendSession);
  if (btnLogout) btnLogout.addEventListener('click', doLogout);

  // ─── LẮNG NGHE HOẠT ĐỘNG USER ─────────────────────────────────────────────
  const ACTIVITY_EVENTS = ['mousemove', 'mousedown', 'keydown', 'scroll', 'touchstart', 'click'];
  ACTIVITY_EVENTS.forEach(evt =>
    document.addEventListener(evt, resetActivityTimer, { passive: true })
  );

  // ─── KHỞI ĐỘNG ────────────────────────────────────────────────────────────
  tickInterval = setInterval(tick, POLL_MS);

  // ─── TICK – gọi mỗi giây ──────────────────────────────────────────────────
  function tick() {
    const elapsed = Date.now() - lastActivity;
    const remaining = TOTAL_MS - elapsed;

    updateTimerBadge(remaining);

    if (remaining <= 0) {
      doLogout();
      return;
    }

    if (remaining <= WARN_BEFORE && !warnShown) {
      showWarning(remaining);
    }

    if (warnShown) {
      warnSecondsLeft = Math.ceil(remaining / 1000);
      if (cdDisplay) cdDisplay.textContent = formatTime(warnSecondsLeft);
    }
  }

  // ─── ĐẶT LẠI BỘ ĐẾM KHI CÓ HOẠT ĐỘNG ────────────────────────────────────
  function resetActivityTimer() {
    lastActivity = Date.now();
    if (warnShown) hideWarning();    // user thao tác → ẩn cảnh báo
  }

  // ─── HIỆN CẢNH BÁO ────────────────────────────────────────────────────────
  function showWarning(remaining) {
    warnShown = true;
    warnSecondsLeft = Math.ceil(remaining / 1000);
    if (cdDisplay) cdDisplay.textContent = formatTime(warnSecondsLeft);
    overlay.classList.add('sm-visible');
    // Đảm bảo overlay không bị che bởi modal khác
    overlay.style.zIndex = '99999';
  }

  function hideWarning() {
    warnShown = false;
    overlay.classList.remove('sm-visible');
  }

  // ─── GIA HẠN PHIÊN ────────────────────────────────────────────────────────
  function extendSession() {
    lastActivity = Date.now();
    hideWarning();
  }

  // ─── ĐĂNG XUẤT ────────────────────────────────────────────────────────────
  function doLogout() {
    clearInterval(tickInterval);

    const logoutBtn = document.getElementById('sm-btn-logout');
    if (logoutBtn) {
      logoutBtn.disabled = true;
      logoutBtn.textContent = 'Đang đăng xuất...';
    }

    // Gọi API logout (fire-and-forget)
    const t = localStorage.getItem('jwt_token');
    if (t) {
      fetch('http://localhost:8080/auth/logout', {
        method: 'POST',
        headers: { 'Authorization': 'Bearer ' + t }
      }).catch(() => { });
    }

    // Xóa thông tin phiên
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('user_id');
    localStorage.removeItem('username');
    localStorage.removeItem('full_name');
    localStorage.removeItem('user_role');

    // Xác định đường dẫn Login.html tương đối từ trang hiện tại
    const loginPath = getLoginPath();
    window.location.href = loginPath;
  }

  // ─── XÁC ĐỊNH ĐƯỜNG DẪN LOGIN TỪ TRANG HIỆN TẠI ─────────────────────────
  function getLoginPath() {
    const path = window.location.pathname;
    // Nếu đang ở /HTML/... → lùi ra gốc rồi vào lại
    if (path.includes('/HTML/')) {
      return 'Login.html';
    }
    return '../HTML/Login.html';
  }

  // ─── CẬP NHẬT BADGE TRÊN TOPBAR ───────────────────────────────────────────
  function updateTimerBadge(remaining) {
    if (!timerBadge) return;
    const secs = Math.max(0, Math.ceil(remaining / 1000));
    timerBadge.textContent = '⏱ ' + formatTime(secs);

    // Đổi màu khi gần hết
    if (secs <= 120) {
      timerBadge.classList.add('sm-badge--warn');
    } else {
      timerBadge.classList.remove('sm-badge--warn');
    }
  }

  // ─── FORMAT mm:ss ──────────────────────────────────────────────────────────
  function formatTime(totalSeconds) {
    const m = Math.floor(totalSeconds / 60);
    const s = totalSeconds % 60;
    return String(m).padStart(2, '0') + ':' + String(s).padStart(2, '0');
  }

  // ─── TẠO BADGE ĐỒNG HỒ TRÊN TOPBAR ───────────────────────────────────────
  function createTimerBadge() {
    const badge = document.createElement('div');
    badge.id = 'sm-timer-badge';
    badge.className = 'sm-badge';
    badge.textContent = '⏱ 15:00';
    badge.title = 'Thời gian phiên làm việc còn lại';

    // Gắn vào topbar__right hoặc user-card nếu có
    function tryAttach() {
      const topbarRight = document.querySelector('.topbar__right');
      const userCard = document.querySelector('.user-card');
      const target = topbarRight || userCard;
      if (target) {
        target.insertBefore(badge, target.firstChild);
        return true;
      }
      return false;
    }

    if (!tryAttach()) {
      // Thử lại sau khi DOM render xong
      const observer = new MutationObserver(() => {
        if (tryAttach()) observer.disconnect();
      });
      observer.observe(document.body, { childList: true, subtree: true });
    }

    return badge;
  }

  // ─── TẠO OVERLAY HTML ─────────────────────────────────────────────────────
  function createWarningOverlay() {
    const el = document.createElement('div');
    el.id = 'sm-overlay';
    el.innerHTML = `
      <div class="sm-dialog" role="alertdialog" aria-modal="true" aria-labelledby="sm-dialog-title">
        <div class="sm-dialog__icon">⏰</div>
        <h3 id="sm-dialog-title" class="sm-dialog__title">Phiên làm việc sắp hết hạn</h3>
        <p class="sm-dialog__body">
          Bạn không có thao tác trong một thời gian dài.<br>
          Phiên làm việc sẽ tự động kết thúc sau:
        </p>
        <div class="sm-countdown" id="sm-countdown-display">02:00</div>
        <p class="sm-dialog__hint">Nhấn <strong>Tiếp tục</strong> để gia hạn thêm 15 phút.</p>
        <div class="sm-dialog__actions">
          <button id="sm-btn-extend" class="sm-btn sm-btn--primary">✅ Tiếp tục làm việc</button>
          <button id="sm-btn-logout"  class="sm-btn sm-btn--ghost">Đăng xuất ngay</button>
        </div>
      </div>
    `;
    return el;
  }

  // ─── INJECT CSS ────────────────────────────────────────────────────────────
  function injectStyles() {
    if (document.getElementById('sm-styles')) return;
    const style = document.createElement('style');
    style.id = 'sm-styles';
    style.textContent = `
      /* ── SESSION MANAGER OVERLAY ── */
      #sm-overlay {
        position: fixed;
        inset: 0;
        background: rgba(15, 23, 42, 0.72);
        backdrop-filter: blur(6px);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 99999;
        opacity: 0;
        pointer-events: none;
        transition: opacity 0.3s ease;
      }
      #sm-overlay.sm-visible {
        opacity: 1;
        pointer-events: all;
      }

      .sm-dialog {
        background: #ffffff;
        border-radius: 20px;
        padding: 40px 36px 32px;
        max-width: 420px;
        width: 90%;
        text-align: center;
        box-shadow: 0 32px 80px rgba(0,0,0,0.25), 0 0 0 1px rgba(255,255,255,0.15);
        transform: translateY(0);
        animation: sm-slide-up 0.35s cubic-bezier(0.34,1.56,0.64,1) forwards;
      }

      @keyframes sm-slide-up {
        from { transform: translateY(30px); opacity: 0; }
        to   { transform: translateY(0);   opacity: 1; }
      }

      .sm-dialog__icon {
        font-size: 48px;
        margin-bottom: 12px;
        animation: sm-pulse 1.2s ease-in-out infinite;
      }

      @keyframes sm-pulse {
        0%, 100% { transform: scale(1); }
        50%       { transform: scale(1.12); }
      }

      .sm-dialog__title {
        font-size: 20px;
        font-weight: 700;
        color: #0f172a;
        margin: 0 0 10px;
      }

      .sm-dialog__body {
        font-size: 14px;
        color: #64748b;
        line-height: 1.6;
        margin: 0 0 16px;
      }

      .sm-countdown {
        font-size: 52px;
        font-weight: 800;
        color: #ef4444;
        letter-spacing: 2px;
        font-variant-numeric: tabular-nums;
        margin: 8px 0 16px;
        text-shadow: 0 0 20px rgba(239,68,68,0.3);
        animation: sm-blink 1s step-end infinite;
      }

      @keyframes sm-blink {
        0%, 100% { opacity: 1; }
        50%       { opacity: 0.6; }
      }

      .sm-dialog__hint {
        font-size: 13px;
        color: #94a3b8;
        margin: 0 0 24px;
      }

      .sm-dialog__actions {
        display: flex;
        flex-direction: column;
        gap: 10px;
      }

      .sm-btn {
        padding: 13px 24px;
        border-radius: 12px;
        font-size: 15px;
        font-weight: 600;
        cursor: pointer;
        border: none;
        transition: transform 0.15s, opacity 0.15s, box-shadow 0.15s;
      }
      .sm-btn:hover { transform: translateY(-2px); }
      .sm-btn:active { transform: translateY(0); }

      .sm-btn--primary {
        background: linear-gradient(135deg, #6366f1, #818cf8);
        color: #fff;
        box-shadow: 0 6px 20px rgba(99,102,241,0.35);
      }
      .sm-btn--primary:hover {
        box-shadow: 0 10px 28px rgba(99,102,241,0.50);
      }

      .sm-btn--ghost {
        background: #f1f5f9;
        color: #64748b;
      }
      .sm-btn--ghost:hover {
        background: #e2e8f0;
      }

      /* ── TIMER BADGE ── */
      .sm-badge {
        display: inline-flex;
        align-items: center;
        gap: 4px;
        background: rgba(99,102,241,0.10);
        color: #6366f1;
        border: 1px solid rgba(99,102,241,0.20);
        border-radius: 999px;
        padding: 5px 13px;
        font-size: 12.5px;
        font-weight: 600;
        font-variant-numeric: tabular-nums;
        letter-spacing: 0.5px;
        cursor: default;
        margin-right: 10px;
        transition: background 0.3s, color 0.3s, border-color 0.3s;
        white-space: nowrap;
        user-select: none;
      }

      .sm-badge--warn {
        background: rgba(239,68,68,0.12);
        color: #ef4444;
        border-color: rgba(239,68,68,0.30);
        animation: sm-badge-pulse 1s ease-in-out infinite;
      }

      @keyframes sm-badge-pulse {
        0%, 100% { box-shadow: 0 0 0 0 rgba(239,68,68,0.3); }
        50%       { box-shadow: 0 0 0 5px rgba(239,68,68,0); }
      }
    `;
    document.head.appendChild(style);
  }

})();

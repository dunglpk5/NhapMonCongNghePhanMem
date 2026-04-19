const editButtons = document.querySelectorAll(".edit-account-btn");
const addButtons = document.querySelectorAll(".open-add-account-btn");
const editAccountModal = document.getElementById("editAccountModal");
const addAccountModal = document.getElementById("addAccountModal");
const editAccountNameInput = document.getElementById("editAccountName");
const editAccountPasswordInput = document.getElementById("editAccountPassword");
const editAccountMeta = document.getElementById("editAccountMeta");
const editAccountForm = document.querySelector("#editAccountModal .modal-form");
const addAccountForm = document.getElementById("addAccountForm");
const addAccountNameInput = document.getElementById("addAccountName");
const closeEditModalButtons = document.querySelectorAll("#editAccountModal .modal-close-btn, #editAccountModal .modal-cancel-btn");
const closeAddModalButtons = document.querySelectorAll("#addAccountModal .modal-close-btn, #addAccountModal .add-modal-cancel-btn");

function openModal(modal) {
    modal.hidden = false;
    document.body.classList.add("modal-open");
}

function closeModal(modal) {
    modal.hidden = true;
    if (editAccountModal.hidden && addAccountModal.hidden) {
    document.body.classList.remove("modal-open");
    }
}

function openEditModal(button) {
    const { accountId, accountName, accountEmail } = button.dataset;
    editAccountNameInput.value = accountName || "";
    editAccountPasswordInput.value = "";
    editAccountMeta.textContent = [accountId, accountEmail].filter(Boolean).join(" • ");
    openModal(editAccountModal);
    editAccountNameInput.focus();
}

function closeEditModal() {
    closeModal(editAccountModal);
}

function openAddModal() {
    addAccountForm.reset();
    openModal(addAccountModal);
    addAccountNameInput.focus();
}

function closeAddModal() {
    closeModal(addAccountModal);
}

editButtons.forEach((button) => {
    button.addEventListener("click", () => openEditModal(button));
});

addButtons.forEach((button) => {
    button.addEventListener("click", openAddModal);
});

closeEditModalButtons.forEach((button) => {
    button.addEventListener("click", closeEditModal);
});

closeAddModalButtons.forEach((button) => {
    button.addEventListener("click", closeAddModal);
});

editAccountModal.addEventListener("click", (event) => {
    if (event.target === editAccountModal) {
    closeEditModal();
    }
});

addAccountModal.addEventListener("click", (event) => {
    if (event.target === addAccountModal) {
    closeAddModal();
    }
});

document.addEventListener("keydown", (event) => {
    if (event.key === "Escape" && !editAccountModal.hidden) {
    closeEditModal();
    }

    if (event.key === "Escape" && !addAccountModal.hidden) {
    closeAddModal();
    }
});

editAccountForm.addEventListener("submit", (event) => {
    event.preventDefault();
    closeEditModal();
});

addAccountForm.addEventListener("submit", (event) => {
    event.preventDefault();
    closeAddModal();
});
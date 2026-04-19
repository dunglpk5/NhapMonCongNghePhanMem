const addClassButtons = document.querySelectorAll(".open-add-class-btn");
const assignTeacherButtons = document.querySelectorAll(".assign-homeroom-btn");
const editClassButtons = document.querySelectorAll(".edit-class-btn");

const addClassModal = document.getElementById("addClassModal");
const assignTeacherModal = document.getElementById("assignTeacherModal");
const editClassModal = document.getElementById("editClassModal");

const addClassForm = document.getElementById("addClassForm");
const assignTeacherForm = document.getElementById("assignTeacherForm");
const editClassForm = document.getElementById("editClassForm");

const addClassNameInput = document.getElementById("addClassName");
const assignTeacherNameInput = document.getElementById("assignTeacherName");
const assignTeacherMeta = document.getElementById("assignTeacherMeta");
const assignTeacherYear = document.getElementById("assignTeacherYear");
const editClassMeta = document.getElementById("editClassMeta");
const editClassTeacherInput = document.getElementById("editClassTeacher");
const editClassSizeInput = document.getElementById("editClassSize");

const closeAddClassButtons = document.querySelectorAll("#addClassModal .modal-close-btn, #addClassModal .modal-cancel-btn");
const closeAssignTeacherButtons = document.querySelectorAll("#assignTeacherModal .modal-close-btn, #assignTeacherModal .modal-cancel-btn");
const closeEditClassButtons = document.querySelectorAll("#editClassModal .modal-close-btn, #editClassModal .modal-cancel-btn");

function openModal(modal) {
    modal.hidden = false;
    document.body.classList.add("modal-open");
}

function closeModal(modal) {
    modal.hidden = true;
    if (addClassModal.hidden && assignTeacherModal.hidden && editClassModal.hidden) {
    document.body.classList.remove("modal-open");
    }
}

function openAddClassModal() {
    addClassForm.reset();
    openModal(addClassModal);
    addClassNameInput.focus();
}

function closeAddClassModal() {
    closeModal(addClassModal);
}

function openAssignTeacherModal(button) {
    const { classId, className, grade, schoolYear } = button.dataset;
    assignTeacherForm.reset();
    assignTeacherMeta.textContent = [classId, className, `Khối ${grade}`].filter(Boolean).join(" • ");
    assignTeacherYear.value = schoolYear || "2025 - 2026";
    openModal(assignTeacherModal);
    assignTeacherNameInput.focus();
}

function closeAssignTeacherModal() {
    closeModal(assignTeacherModal);
}

function openEditClassModal(button) {
    const { classId, className, grade, homeroomTeacher, classSize } = button.dataset;
    editClassForm.reset();
    editClassMeta.textContent = [classId, className, `Khối ${grade}`].filter(Boolean).join(" • ");
    editClassTeacherInput.value = homeroomTeacher || "Chọn giáo viên";
    editClassSizeInput.value = classSize || "";
    openModal(editClassModal);
    editClassTeacherInput.focus();
}

function closeEditClassModal() {
    closeModal(editClassModal);
}

addClassButtons.forEach((button) => {
    button.addEventListener("click", openAddClassModal);
});

assignTeacherButtons.forEach((button) => {
    button.addEventListener("click", () => openAssignTeacherModal(button));
});

editClassButtons.forEach((button) => {
    button.addEventListener("click", () => openEditClassModal(button));
});

closeAddClassButtons.forEach((button) => {
    button.addEventListener("click", closeAddClassModal);
});

closeAssignTeacherButtons.forEach((button) => {
    button.addEventListener("click", closeAssignTeacherModal);
});

closeEditClassButtons.forEach((button) => {
    button.addEventListener("click", closeEditClassModal);
});

addClassModal.addEventListener("click", (event) => {
    if (event.target === addClassModal) closeAddClassModal();
});

assignTeacherModal.addEventListener("click", (event) => {
    if (event.target === assignTeacherModal) closeAssignTeacherModal();
});

editClassModal.addEventListener("click", (event) => {
    if (event.target === editClassModal) closeEditClassModal();
});

document.addEventListener("keydown", (event) => {
    if (event.key === "Escape" && !addClassModal.hidden) closeAddClassModal();
    if (event.key === "Escape" && !assignTeacherModal.hidden) closeAssignTeacherModal();
    if (event.key === "Escape" && !editClassModal.hidden) closeEditClassModal();
});

addClassForm.addEventListener("submit", (event) => {
    event.preventDefault();
    closeAddClassModal();
});

assignTeacherForm.addEventListener("submit", (event) => {
    event.preventDefault();
    closeAssignTeacherModal();
});

editClassForm.addEventListener("submit", (event) => {
    event.preventDefault();
    closeEditClassModal();
});
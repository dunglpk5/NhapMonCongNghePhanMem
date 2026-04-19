(() => {
  const addStudentModal = document.getElementById("addStudentModal");
  const editStudentModal = document.getElementById("editStudentModal");
  const addStudentOpenButtons = [
    document.getElementById("openAddStudentBtn"),
    document.getElementById("openAddStudentBtnSide"),
  ].filter(Boolean);
  const editStudentButtons = document.querySelectorAll(".edit-student-btn");
  const editStudentForm = document.getElementById("editStudentForm");
  const editStudentNameInput = document.getElementById("editStudentName");
  const editStudentClassInput = document.getElementById("editStudentClass");
  const editParentPhoneInput = document.getElementById("editParentPhone");

  const allModals = [addStudentModal, editStudentModal].filter(Boolean);

  if (allModals.length === 0) return;

  const openModal = (modal) => {
    modal.classList.add("is-open");
    modal.setAttribute("aria-hidden", "false");
    document.body.classList.add("modal-open");
  };

  const closeModal = (modal) => {
    modal.classList.remove("is-open");
    modal.setAttribute("aria-hidden", "true");

    if (!allModals.some((item) => item.classList.contains("is-open"))) {
      document.body.classList.remove("modal-open");
    }
  };

  addStudentOpenButtons.forEach((button) => {
    button.addEventListener("click", () => openModal(addStudentModal));
  });

  editStudentButtons.forEach((button) => {
    button.addEventListener("click", () => {
      const { studentName, studentClass, parentPhone } = button.dataset;
      editStudentForm.reset();
      editStudentNameInput.value = studentName || "";
      editStudentClassInput.value = studentClass || "10A1";
      editParentPhoneInput.value = parentPhone || "";
      openModal(editStudentModal);
      editStudentNameInput.focus();
    });
  });

  allModals.forEach((modal) => {
    modal.querySelectorAll("[data-close-modal]").forEach((button) => {
      button.addEventListener("click", () => closeModal(modal));
    });
  });

  editStudentForm?.addEventListener("submit", (event) => {
    event.preventDefault();
    closeModal(editStudentModal);
  });

  document.addEventListener("keydown", (event) => {
    if (event.key !== "Escape") return;

    allModals.forEach((modal) => {
      if (modal.classList.contains("is-open")) {
        closeModal(modal);
      }
    });
  });
})();

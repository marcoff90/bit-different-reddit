(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
  .forEach(function (form) {
    form.addEventListener('submit', function (event) {
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
      }

      form.classList.add('was-validated')
    }, false)
  })
})();


$(document).ready(function () {

  window.setTimeout(function () {
    $(".alert").fadeTo(1000, 0).slideUp(1000, function () {
      $(this).remove();
    });
  }, 3500);

});
//
// $(document).ready( function () {
//   $('#myTable').dataTable({searching: false, info: false, ordering: false, lengthChange: false, responsive: true});
//   $('#myTable').DataTable();
// } );









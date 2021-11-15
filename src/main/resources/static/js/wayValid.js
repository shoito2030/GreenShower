// 手段必須入力切り替え機能
const way1 = document.getElementById("way1");
const way2 = document.getElementById("way2");
const way3 = document.getElementById("way3");

const date_absence_from = document.getElementById("date_absence_from");
const date_absence_to = document.getElementById("date_absence_to");

const leave_early_date = document.getElementById("leave_early_date");
const attendance_date = document.getElementById("attendance_date");

way1.addEventListener('change', function () {
    if (way1.checked) {
        //入力制限の為の属性付与
        date_absence_from.removeAttribute("disabled");
        date_absence_to.removeAttribute("disabled");
        way2.checked = false;
        leave_early_date.setAttribute("disabled", true);
        leave_early_date.value = '';
        way3.checked = false;
        attendance_date.setAttribute("disabled", true);
        attendance_date.value = '';
        //required属性の付与
        date_absence_from.setAttribute("required", true);
        date_absence_to.setAttribute("required", true);
        leave_early_date.removeAttribute("required");
        attendance_date.removeAttribute("required");
    } else {
        //入力制限の為の属性付与
        date_absence_from.setAttribute("disabled", true);
        date_absence_from.value = '';
        date_absence_to.setAttribute("disabled", true);
        date_absence_to.value = '';
        //required属性の付与
        leave_early_date.setAttribute("required", true);
        attendance_date.setAttribute("required", true);
    }
})

way2.addEventListener('change', function () {
    if (way2.checked) {
        //入力制限の為の属性付与
        leave_early_date.removeAttribute("disabled");
        way1.checked = false;
        date_absence_from.setAttribute("disabled", true);
        date_absence_from.value = '';
        date_absence_to.setAttribute("disabled", true);
        date_absence_to.value = '';
        //required属性の付与
        date_absence_from.removeAttribute("required");
        date_absence_to.removeAttribute("required");
        leave_early_date.setAttribute("required", true);
        attendance_date.removeAttribute("required");
        if (document.getElementById("way3").checked) {
            attendance_date.setAttribute("required", true);
        }
    } else {
        //入力制限の為の属性付与
        leave_early_date.setAttribute("disabled", true);
        leave_early_date.value = '';
        //required属性の付与
        date_absence_from.setAttribute("required", true);
        date_absence_to.setAttribute("required", true);
        leave_early_date.setAttribute("required", true);
        attendance_date.setAttribute("required", true);
        if (document.getElementById("way3").checked) {
            date_absence_from.removeAttribute("required");
            date_absence_to.removeAttribute("required");
            leave_early_date.removeAttribute("required");
        }
    }
})


way3.addEventListener('change', function () {
    if (way3.checked) {
        //入力制限の為の属性付与
        attendance_date.removeAttribute("disabled");
        way1.checked = false;
        date_absence_from.setAttribute("disabled", true);
        date_absence_from.value = '';
        date_absence_to.setAttribute("disabled", true);
        date_absence_to.value = '';
        //required属性の付与
        date_absence_from.removeAttribute("required");
        date_absence_to.removeAttribute("required");
        leave_early_date.removeAttribute("required");
        attendance_date.setAttribute("required", true);
        if (way2.checked) {
            leave_early_date.setAttribute("required", true);
        }
    } else {
        //入力制限の為の属性付与
        attendance_date.setAttribute("disabled", true);
        attendance_date.value = '';
        //required属性の付与
        date_absence_from.setAttribute("required", true);
        date_absence_to.setAttribute("required", true);
        leave_early_date.setAttribute("required", true);
        attendance_date.setAttribute("required", true);
        if (way2.checked) {
            date_absence_from.removeAttribute("required");
            date_absence_to.removeAttribute("required");
            attendance_date.removeAttribute("required");
        }
    }
})


window.onload = function () {
    //disabled・required属性の付与
    date_absence_from.setAttribute("disabled", true);
    date_absence_to.setAttribute("disabled", true);
    leave_early_date.setAttribute("disabled", true);
    attendance_date.setAttribute("disabled", true);
    date_absence_from.setAttribute("required", true);
    date_absence_to.setAttribute("required", true);
    leave_early_date.setAttribute("required", true);
    attendance_date.setAttribute("required", true);

    if (way1.checked) {
        date_absence_from.removeAttribute("disabled");
        date_absence_to.removeAttribute("disabled");
        leave_early_date.removeAttribute("required");
        attendance_date.removeAttribute("required");
    } else if (way2.checked) {
        date_absence_from.removeAttribute("required");
        date_absence_to.removeAttribute("required");
        leave_early_date.removeAttribute("disabled");
        attendance_date.removeAttribute("required");
    } else if (way3.checked) {
        date_absence_from.removeAttribute("required");
        date_absence_to.removeAttribute("required");
        leave_early_date.removeAttribute("required");
        attendance_date.removeAttribute("disabled");
    }
}
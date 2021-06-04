$(document).ready(function () {
    auth()
    load();
});

function auth() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/auth',
        dataType: 'json'
    }).done(function (data) {
        let a1 = $('#a1');
        let a2 = $('#a2');
        if (data.name === undefined) {
            a1.text("Войти").attr("href", "http://localhost:8080/todo/login.html");
            a2.text("Регистрация").attr("href", "http://localhost:8080/todo/reg.html");
        } else {
            a1.text(data.name);
            a2.text("Выйти").attr("onclick", "logout();");
        }
    }).fail(function () {
        alert('Ошибка авторизации');
    });
}

function logout() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/logout',
        success: function(){
            auth();
        },
        error: function(){
            alert('Ошибка выхода');
        }
    });
}

function load() {
    let showAll = $('#show-all')[0].checked;
    console.log("showAll = " + showAll);
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/item',
        data: {showAll: showAll},
        dataType: 'json'
    }).done(function (data) {
        let table = '';
        data.forEach(el => {
            table += '<tr>';
            table += '<td class="align-middle">' + el.id + '</td>';
            table += '<td class="align-middle">' + el.description + '</td>';
            table += '<td class="align-middle">' + el.created + '</td>';
            if (el.user === undefined) {
                table += '<td class="align-middle">Anonymous</td>';
            } else {
                table += '<td class="align-middle">' + el.user.name + '</td>';
            }
            if (el.done === false) {
                table += '<td style="text-align: center;">' +
                    '<div style="vertical-align: middle;">' +
                    '<label for="done">' +
                    '<input type="checkbox" class="form-check-input" id="done" title="Выполнить" onchange="checkItem(' + el.id + ');" ' +
                    'style="vertical-align: middle; margin-left: -0.5rem;"></label>' +
                    '<i class="bi bi-x" id="delete" title="Удалить" onclick="deleteItem(' + el.id + ');" style="font-size: 1.8rem; color: #007bff; float: right;"></i>' +
                    '</td>';
            } else {
                table += '<td style="text-align: center;">' +
                    '<i class="bi bi-x" id="delete" title="Удалить" onclick="deleteItem(' + el.id + ');" style="font-size: 1.8rem; color: #007bff; float: right;"></i></div>' +
                    '</td>';
            }
            table += '</tr>';
        });
        $('#table tbody').html(table);
    }).fail(function (err) {
        alert(err);
    });
}

function addItem() {
    let desc = $('#desc').val();
    if (desc === '') {
        alert('Добавьте задание');
        return;
    }
    const item = {
        description: desc,
    };
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8080/todo/item',
        data: JSON.stringify(item)
    }).done(function () {
        load();
    }).fail(function () {
        alert("Не удалось добавить задание");
    });
}

function checkItem(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/item',
        data: {id: id},
    }).done(function () {
        load();
    }).fail(function () {
        alert("Не удалось выбрать задание");
    });
}

function deleteItem(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/delete',
        data: {id: id},
    }).done(function () {
        load();
    }).fail(function () {
        alert("Не удалось удалить задание");
    });
}

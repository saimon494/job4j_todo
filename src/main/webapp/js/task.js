$(document).ready(function () {
    show();
    setInterval('show()', 2000);
});

function show() {
    console.log('show()');
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/item'
    }).done(function (data) {
        const tbody = document.querySelector('tbody');
        const trs = document.querySelectorAll('tbody tr');
        const showAll = document.querySelector('#show_all');
        console.log(showAll.checked);
        $.each(trs, function (i, e) {
            console.log('e.remove()');
            e.remove();
        });
        $.each(JSON.parse(data), function (i, e) {
            console.log(e);
            if (!showAll.checked && e.done) {
                console.log('return');
                return;
            }
            const tr = document.createElement('tr');
            const num = document.createElement('td');
            const desc = document.createElement('td');
            const date = document.createElement('td');
            const input = document.createElement('input');
            const delIcon = document.createElement('i');
            num.innerText = e.id;
            desc.innerText = e.description;
            date.innerText = e.created;
            input.setAttribute("type", "checkbox");
            input.setAttribute("class", "form-check-input");
            input.setAttribute("id", "done");
            input.setAttribute("title", "Выполнить");
            input.setAttribute("style", "vertical-align: middle; position: relative;");
            if (e.done) {
                input.setAttribute("checked", "true");
            } else {
                input.setAttribute("value", "checked");
            }
            delIcon.setAttribute("class", "bi bi-x");
            delIcon.setAttribute("id", "delete");
            delIcon.setAttribute("title", "Удалить");
            delIcon.setAttribute("style", "font-size: 1.8rem; color: #007bff; vertical-align: middle; position: relative;");
            tr.append(num);
            tr.append(desc);
            tr.append(date);
            tr.append(input);
            tr.append(delIcon);
            tbody.append(tr);
            checkItem(e);
            deleteItem(e.id);
        });
    }).fail(function (err) {
        alert(err);
    });
}

function addItem() {
    console.log('addItem()');
    const desc = document.querySelector('#description');
    if (desc.value === '') {
        alert('Добавьте задание');
        return;
    }
    const item = {
        description: desc.value,
    };
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8080/todo/item',
        data: JSON.stringify(item)
    }).done(function () {
        desc.value = '';
    }).fail(function () {
        alert("Не удалось добавить задание");
    });
}

function checkItem(item) {
    console.log('checkItem()');
    $("tr #done").change(function () {
        if (this.checked) {
            item.done = true;
        } else {
            item.done = false;
        }
        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/todo/item',
            data: JSON.stringify(item)
        }).done(function () {
            show();
        }).fail(function () {
            alert("Не удалось выбрать задание");
        });
        console.log(item);
    });
}

function deleteItem(id) {
    console.log('deleteItem()');
    var row = $(this).closest('tr');
    // var row = $(this).parent().parent();
    $("tr #delete").click(function () {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/todo/delete',
            data: {id: id},
        }).done(function () {
            row.remove();
            // show();
        }).fail(function () {
            alert("Не удалось удалить задание");
        });
    });
}

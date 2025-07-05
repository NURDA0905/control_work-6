<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Записи на ${date}</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }
        td, th { border: 1px solid #ccc; padding: 8px; text-align: left; }
.primary { color: green; }
.secondary { color: blue; }
</style>
</head>
<body>
<h2>Список пациентов на ${date}</h2>

<#if patients?size == 0>
<p>Пациентов на этот день нет.</p>
<#else>
<table>
<tr>
<th>Время</th>
<th>ФИО</th>
<th>Тип</th>
<th>Анамнез</th>
<th>Действие</th>
</tr>
<#list patients as p>
<tr>
<td>${p.time}</td>
<td>${p.fullName}</td>
<td class="${p.type == 'первичный'?then('primary','secondary')}">${p.type}</td>
                <td>${p.symptoms}</td>
                <td><a href="/delete?id=${p.id}&date=${date}">Удалить</a></td>
            </tr>
            </#list>
        </table>
    </#if>

    <h3>Добавить пациента</h3>
    <form action="/add?date=${date}" method="post">
        <label>ФИО: <input type="text" name="fullName" required></label><br>
        <label>Дата рождения: <input type="date" name="birthDate" required></label><br>
        <label>Время визита: <input type="time" name="time" required></label><br>
        <label>Тип пациента:
            <select name="type" required>
                <option value="первичный">первичный</option>
                <option value="вторичный">вторичный</option>
            </select>
        </label><br>
        <label>Анамнез: <textarea name="symptoms" required></textarea></label><br>
        <button type="submit">Сохранить</button>
    </form>

    <br><a href="/calendar">Назад к календарю</a>
</body>
</html>

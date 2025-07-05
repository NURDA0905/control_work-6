<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Расписание врача на ${month}</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        td { border: 1px solid #999; padding: 10px; text-align: center; vertical-align: top; }
.today { background-color: #fdd; }
.primary { color: green; font-weight: bold; }
.secondary { color: blue; }
</style>
</head>
<body>
<h1>Расписание врача на ${month}</h1>
<table>
<tr>
<#list ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"] as day>
<th>${day}</th>
</#list>
</tr>
<#list calendar as week>
<tr>
<#list week as day>
<td class="${day.isToday?then('today','')}">
                        <#if day.date??>
                            <a href="/schedule?date=${day.date}">
                                <strong>${day.dayNumber}</strong>
                            </a><br>
                            <#if day.patients??>
                                Пациентов: ${day.patients?size}<br>
                                <#list day.patients as p>
                                    <span class="${p.type == 'первичный'?then('primary','secondary')}">
                                        ${p.time} — ${p.fullName}
                                    </span><br>
                                </#list>
                            </#if>
                        </#if>
                    </td>
                </#list>
            </tr>
        </#list>
    </table>
</body>
</html>

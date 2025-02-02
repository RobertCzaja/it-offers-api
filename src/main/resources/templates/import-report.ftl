<!DOCTYPE html>
<html>
<style>
    table, th, td {
        border:1px solid black;
    }
    .offers-count-success {
        background-color: green;
    }
</style>
<head>
    <title>${title}</title>
</head>
<body>
<h1>${title}</h1>
<table>
    <tr>
        <td>Scraping ID</td>
        <td><code>${scrapingId}</code></td>
    </tr>
    <tr>
        <td>Day</td>
        <td>${day}</td>
    </tr>
    <tr>
        <td>From</td>
        <td>${from}</td>
    </tr>
    <tr>
        <td>To</td>
        <td>${to}</td>
    </tr>
    <tr>
        <td>Duration</td>
        <td>${duration}</td>
    </tr>
</table>
<br />
<table>
    <#list technologies?keys as technology>
        <tr class="<#if technologies[technology].new != 0>offers-count-success</#if>">
            <td>${technology}</td>
            <td>${technologies[technology].fetched}</td>
            <td >${technologies[technology].new}</td>
        </tr>
    </#list>
</table>


</body>
</html>
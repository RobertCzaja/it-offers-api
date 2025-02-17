<!DOCTYPE html>
<html lang="eng">
<body>
<table style="border:1px solid black;">
    <tr style="border:1px solid black;">
        <td style="border:1px solid black;">Scraping ID</td>
        <td style="border:1px solid black;"><code>${scrapingId}</code></td>
    </tr>
    <tr style="border:1px solid black;">
        <td style="border:1px solid black;">Day</td>
        <td style="border:1px solid black;">${day}</td>
    </tr>
    <tr style="border:1px solid black;">
        <td style="border:1px solid black;">From</td>
        <td style="border:1px solid black;">${from}</td>
    </tr>
    <tr style="border:1px solid black;">
        <td style="border:1px solid black;">To</td>
        <td style="border:1px solid black;">${to}</td>
    </tr>
    <tr style="border:1px solid black;">
        <td style="border:1px solid black;">Duration</td>
        <td style="border:1px solid black;">${duration}</td>
    </tr>
</table>
<br />
<table style="border:1px solid black;">
    <tr style="border:1px solid black;">
        <th style="border:1px solid black;">
            <strong>technology</strong>
        </th>
        <th style="border:1px solid black;">
            <strong>fetched</strong>
        </th>
        <th style="border:1px solid black;">
            <strong>new</strong>
        </th>
        <th style="border:1px solid black;">
            <strong>errors</strong>
        </th>
    </tr>
    <#list technologies?keys as technology>
        <tr style="border:1px solid black; <#if technologies[technology].new != 0>background-color: limegreen;</#if>">
            <td style="border:1px solid black; min-width: 50px">
                <strong>${technology}</strong>
            </td>
            <td style="border:1px solid black; min-width: 50px">
                ${technologies[technology].fetched}
            </td>
            <td style="border:1px solid black; min-width: 50px">
                ${technologies[technology].new}
            </td>
            <td style="border:1px solid black; min-width: 300px">
                <ul style="list-style-type: none; margin-left: -20px">
                    <#list technologies[technology].errors as error>
                        <li>
                            <strong style="color: red;">${error.class}</strong>:
                            ${error.message}
                        </li>
                    </#list>
                </ul>
            </td>
        </tr>
    </#list>
</table>
</body>
</html>
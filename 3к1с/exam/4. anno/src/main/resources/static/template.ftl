<form method="${method}" action="${action}">
    <#list inputs as element>
        <input type="${element.type()}" name="${element.name()}" placeholder="${element.placeholder()}">
    </#list>
</form>
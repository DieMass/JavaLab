<%--
  Created by IntelliJ IDEA.
  User: diemass
  Date: 23.10.2019
  Time: 23:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<html>
<head>
  <title>Работа с текстом</title>
</head>
<body>

<form method="post">
  <div class="form-group">
    <label for="exampleFormControlTextarea1">Your text</label>
    <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="text"></textarea>
  </div>

  <div class="form-group col-md-4">
    <label for="inputState">Choose</label>
    <select id="inputState" class="form-control" name="input">
      <option selected>Characters count</option>
      <option>Word count</option>
      <option>Sentence count</option>
      <option>Paragraph count</option>
    </select>
  </div>

  <button type="submit" class="btn btn-primary" >Process</button>
</form>
</body>
</html>

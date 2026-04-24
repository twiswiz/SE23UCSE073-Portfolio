<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.hr.attendance.Attendance" %>
<%
   String username = (String) session.getAttribute("username");
   if (username == null) {
       response.sendRedirect(request.getContextPath()+"/login.jsp");
       return;
   }
%>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Attendance</title>
  <style>
    table{border-collapse:collapse;width:100%}
    th,td{border:1px solid #ccc;padding:6px;text-align:left}
    th{background:#f1f1f1}
    .button{display:inline-block;padding:6px 10px;background:#007bff;color:#fff;text-decoration:none;border-radius:4px}
    .button:hover{background:#0056b3}
    .msg{padding:10px;margin:8px 0;border-radius:4px}
    .success{background:#e6ffed;color:#084f2c;border:1px solid #b7f0c9}
    .warn{background:#fff4e6;color:#6a3a00;border:1px solid #ffd89b}
  </style>
  <script>
    function disableOnce(btn) {
        btn.disabled = true;
        setTimeout(function(){ btn.disabled = false; }, 1200);
        return true;
    }
  </script>
</head>
<body>

<h2>Your Attendance Log</h2>

<p>Signed in as: <strong><%= username %></strong></p>

<%
    Attendance lastEvent = (Attendance) request.getAttribute("lastEvent");
    String nextAction = "IN"; // default if no events
    String lastText = "No previous events";
    if (lastEvent != null) {
        lastText = lastEvent.getEventType() + " at " + lastEvent.getEventTime();
        // toggle: if last was IN, next should be OUT
        if ("IN".equalsIgnoreCase(lastEvent.getEventType())) {
            nextAction = "OUT";
        } else {
            nextAction = "IN";
        }
    }
%>

<p>Last: <em><%= lastText %></em></p>

<% String msg = (String) request.getAttribute("attMsg");
   if (msg != null) { %>
    <div class="msg success"><%= msg %></div>
<% } %>

<form action="<%= request.getContextPath() %>/attendance/clock" method="post" onsubmit="return disableOnce(this.elements['doBtn']);">
    <input type="hidden" name="action" value="<%= nextAction %>" />
    Note (optional): <input type="text" name="note" placeholder="reason (lunch, remote)" />
    <button class="button" name="doBtn" type="submit"><%= ( "IN".equals(nextAction) ? "Clock In" : "Clock Out" ) %></button>
</form>

<br/>
<a class="button" href="<%= request.getContextPath() %>/employee/">Employee Portal</a>

<br><br>

<table>
<tr><th>ID</th><th>Type</th><th>Time</th><th>Note</th></tr>
<%
    List<Attendance> list = (List<Attendance>) request.getAttribute("history");
    if (list != null && !list.isEmpty()) {
        for (Attendance a : list) {
%>
<tr>
  <td><%= a.getId() %></td>
  <td><%= a.getEventType() %></td>
  <td><%= a.getEventTime() %></td>
  <td><%= a.getNote() != null ? a.getNote() : "" %></td>
</tr>
<%
        }
    } else {
%>
<tr><td colspan="4">No attendance events yet.</td></tr>
<%
    }
%>
</table>

</body>
</html>

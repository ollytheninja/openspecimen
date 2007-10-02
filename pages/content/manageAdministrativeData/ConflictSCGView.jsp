<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List,edu.wustl.catissuecore.util.global.Constants,edu.wustl.catissuecore.util.global.Utility"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ page import="edu.wustl.catissuecore.util.global.Constants" %>
<%@ page import="edu.wustl.catissuecore.domain.Participant" %>


<%
	boolean readOnlyValue=true;
	String divHeight="330";

%>

<html:errors />

<table summary="" cellpadding="0" cellspacing="" border="0" width="100%" style="table-layout:fixed" height="40%">
	
	<tr>
		<td width="50%" class="formFieldNoBordersBold">
			<bean:message key="caTies.conflict.existing.report"/>:
		</td>
		<td width="50%" class="formFieldNoBordersBold">
			<bean:message key="caTies.conflict.new.report"/>:
		</td>
	</tr>
		
	<tr>
		<td width="50%">
		 	
				<div style="height:<%=divHeight%>;width:100%;; overflow:auto;;border: 1px solid #111;"><PRE><bean:write name="conflictSCGForm" property="existingConflictedReport"/></PRE>
				</div>
		
		 </td>
		 <td width="50%">
		 	
				<div style="height:<%=divHeight%>;width:100%; overflow:auto;;border: 1px solid #111; "><PRE><bean:write name="conflictSCGForm" property="newConflictedReport"/></PRE>
				</div>
		 </td>	
	</tr>
	
</table>






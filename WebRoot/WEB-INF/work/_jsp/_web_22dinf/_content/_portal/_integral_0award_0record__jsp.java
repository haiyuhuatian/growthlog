/*
 * JSP generated by Resin Professional 4.0.54 (built Tue, 03 Oct 2017 01:42:53 PDT)
 */

package _jsp._web_22dinf._content._portal;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _integral_0award_0record__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  private boolean _caucho_isNotModified;
  private com.caucho.jsp.PageManager _jsp_pageManager;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    com.caucho.jsp.PageContextImpl pageContext = _jsp_pageManager.allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);

    TagState _jsp_state = new TagState();

    try {
      _jspService(request, response, pageContext, _jsp_application, session, _jsp_state);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_state.release();
      _jsp_pageManager.freePageContext(pageContext);
    }
  }
  
  private void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response,
              com.caucho.jsp.PageContextImpl pageContext,
              javax.servlet.ServletContext application,
              javax.servlet.http.HttpSession session,
              TagState _jsp_state)
    throws Throwable
  {
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    javax.servlet.jsp.tagext.JspTag _jsp_parent_tag = null;
    com.caucho.jsp.PageContextImpl _jsp_parentContext = pageContext;
    response.setContentType("text/html; charset=UTF-8");
    com.caucho.jstl.rt.FormatDateTag _jsp_FormatDateTag_0 = null;

    out.write(_jsp_string0, 0, _jsp_string0.length);
    pageContext.defaultSetOrRemove("cache_time", "86400");
    out.write(_jsp_string1, 0, _jsp_string1.length);
    pageContext.defaultSetOrRemove("cache_scope", "application");
    out.write(_jsp_string1, 0, _jsp_string1.length);
    pageContext.defaultSetOrRemove("base", _caucho_expr_0.evalObject(_jsp_env));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    _caucho_expr_1.print(out, _jsp_env, false);
    out.write(_jsp_string3, 0, _jsp_string3.length);
    _caucho_expr_1.print(out, _jsp_env, false);
    out.write(_jsp_string4, 0, _jsp_string4.length);
    _caucho_expr_1.print(out, _jsp_env, false);
    out.write(_jsp_string5, 0, _jsp_string5.length);
    _caucho_expr_1.print(out, _jsp_env, false);
    out.write(_jsp_string6, 0, _jsp_string6.length);
    _caucho_expr_2.print(out, _jsp_env, false);
    out.write(_jsp_string7, 0, _jsp_string7.length);
    _caucho_expr_3.print(out, _jsp_env, false);
    out.write(_jsp_string8, 0, _jsp_string8.length);
    _caucho_expr_4.print(out, _jsp_env, false);
    out.write(_jsp_string9, 0, _jsp_string9.length);
    _caucho_expr_5.print(out, _jsp_env, false);
    out.write(_jsp_string10, 0, _jsp_string10.length);
    _caucho_expr_6.print(out, _jsp_env, false);
    out.write(_jsp_string11, 0, _jsp_string11.length);
    _caucho_expr_7.print(out, _jsp_env, false);
    out.write(_jsp_string12, 0, _jsp_string12.length);
    _caucho_expr_8.print(out, _jsp_env, false);
    out.write(_jsp_string13, 0, _jsp_string13.length);
    _caucho_expr_9.print(out, _jsp_env, false);
    out.write(_jsp_string14, 0, _jsp_string14.length);
    _jsp_FormatDateTag_0 = _jsp_state.get_jsp_FormatDateTag_0(pageContext, _jsp_parent_tag);
    _jsp_FormatDateTag_0.setValue(_caucho_expr_10.evalObject(_jsp_env));
    _jsp_FormatDateTag_0.doEndTag();
    out.write(_jsp_string15, 0, _jsp_string15.length);
    _caucho_expr_11.print(out, _jsp_env, false);
    out.write(_jsp_string16, 0, _jsp_string16.length);
    _jsp_FormatDateTag_0.setValue(_caucho_expr_12.evalObject(_jsp_env));
    _jsp_FormatDateTag_0.doEndTag();
    out.write(_jsp_string17, 0, _jsp_string17.length);
    _caucho_expr_13.print(out, _jsp_env, false);
    out.write(_jsp_string18, 0, _jsp_string18.length);
    if (_caucho_expr_14.evalBoolean(_jsp_env)) {
      out.write(_jsp_string19, 0, _jsp_string19.length);
      _caucho_expr_15.print(out, _jsp_env, false);
      out.write(_jsp_string20, 0, _jsp_string20.length);
    }
    out.write(_jsp_string21, 0, _jsp_string21.length);
    _caucho_expr_2.print(out, _jsp_env, false);
    out.write(_jsp_string22, 0, _jsp_string22.length);
    _caucho_expr_3.print(out, _jsp_env, false);
    out.write(_jsp_string23, 0, _jsp_string23.length);
  }

  private com.caucho.make.DependencyContainer _caucho_depends
    = new com.caucho.make.DependencyContainer();

  public java.util.ArrayList<com.caucho.vfs.Dependency> _caucho_getDependList()
  {
    return _caucho_depends.getDependencies();
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    _caucho_depends.add(depend);
  }

  protected void _caucho_setNeverModified(boolean isNotModified)
  {
    _caucho_isNotModified = true;
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;

    if (_caucho_isNotModified)
      return false;

    if (com.caucho.server.util.CauchoSystem.getVersionId() != -2496007962829123395L)
      return true;

    return _caucho_depends.isModified();
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
    TagState tagState;
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/content/portal/integral_award_record.jsp"), -2600704432671292938L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/content/common/taglib.jsp"), 3690004229260259696L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    depend = new com.caucho.vfs.Depend(mergePath.lookup("jar:file:/e:/resin-pro-4.0.54/lib/resin.jar!/com/caucho/jstl/fmt.tld"), 8594578285720415164L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    _caucho_depends.add(new com.caucho.make.ClassDependency("com.caucho.jstl.rt.FormatDateTag", -4037758247139937686L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  final static class TagState {
    private com.caucho.jstl.rt.FormatDateTag _jsp_FormatDateTag_0;

    final com.caucho.jstl.rt.FormatDateTag get_jsp_FormatDateTag_0(PageContext pageContext, javax.servlet.jsp.tagext.JspTag _jsp_parent_tag) throws Throwable
    {
      if (_jsp_FormatDateTag_0 == null) {
        _jsp_FormatDateTag_0 = new com.caucho.jstl.rt.FormatDateTag();
        _jsp_FormatDateTag_0.setPageContext(pageContext);
        if (_jsp_parent_tag instanceof javax.servlet.jsp.tagext.Tag)
          _jsp_FormatDateTag_0.setParent((javax.servlet.jsp.tagext.Tag) _jsp_parent_tag);
        else if (_jsp_parent_tag instanceof javax.servlet.jsp.tagext.SimpleTag)
          _jsp_FormatDateTag_0.setParent(new javax.servlet.jsp.tagext.TagAdapter((javax.servlet.jsp.tagext.SimpleTag) _jsp_parent_tag));
        else
          _jsp_FormatDateTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_FormatDateTag_0.setPattern("yyyy-MM-dd HH:mm:ss");
      }

      return _jsp_FormatDateTag_0;
    }

    void release()
    {
      if (_jsp_FormatDateTag_0 != null) {
        _jsp_FormatDateTag_0.release();
        _jsp_FormatDateTag_0 = null;
      }
    }
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void caucho_init(ServletConfig config)
  {
    try {
      com.caucho.server.webapp.WebApp webApp
        = (com.caucho.server.webapp.WebApp) config.getServletContext();
      init(config);
      if (com.caucho.jsp.JspManager.getCheckInterval() >= 0)
        _caucho_depends.setCheckInterval(com.caucho.jsp.JspManager.getCheckInterval());
      _jsp_pageManager = webApp.getJspApplicationContext().getPageManager();
      com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
      manager.addTaglibFunctions(_jsp_functionMap, "s", "/struts-tags");
      manager.addTaglibFunctions(_jsp_functionMap, "c", "http://java.sun.com/jsp/jstl/core");
      manager.addTaglibFunctions(_jsp_functionMap, "fmt", "http://java.sun.com/jsp/jstl/fmt");
      manager.addTaglibFunctions(_jsp_functionMap, "g", "/WEB-INF/czrz-taglib.tld");
      manager.addTaglibFunctions(_jsp_functionMap, "cache", "http://www.opensymphony.com/oscache");
      com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.InitPageContextImpl(webApp, this);
      _caucho_expr_0 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${pageContext.request.contextPath}");
      _caucho_expr_1 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${base }");
      _caucho_expr_2 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${base}");
      _caucho_expr_3 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${awardRecord.award_id }");
      _caucho_expr_4 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${awardRecord.realname }");
      _caucho_expr_5 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${awardRecord.mobile }");
      _caucho_expr_6 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${province }");
      _caucho_expr_7 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${city }");
      _caucho_expr_8 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${area }");
      _caucho_expr_9 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${awardRecord.detailed_address }");
      _caucho_expr_10 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${awardRecord.create_time }");
      _caucho_expr_11 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${awardRecord.verify == 0?'\u5f85\u5ba1\u6838':awardRecord.verify == 1?'\u901a\u8fc7':'\u4e0d\u901a\u8fc7' }");
      _caucho_expr_12 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${awardRecord.verify_time }");
      _caucho_expr_13 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${awardRecord.remark }");
      _caucho_expr_14 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${awardRecord.verify==1 }");
      _caucho_expr_15 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${empty(awardRecord.status)?'':awardRecord.status==0?'\u5f85\u53d1\u8d27':awardRecord.status==1?'\u5df2\u53d1\u8d27':awardRecord.status == 2?'\u5f85\u8bc4\u4ef7':'\u5df2\u5b8c\u6210' }");
    } catch (Exception e) {
      throw com.caucho.config.ConfigException.create(e);
    }
  }
  private static com.caucho.el.Expr _caucho_expr_0;
  private static com.caucho.el.Expr _caucho_expr_1;
  private static com.caucho.el.Expr _caucho_expr_2;
  private static com.caucho.el.Expr _caucho_expr_3;
  private static com.caucho.el.Expr _caucho_expr_4;
  private static com.caucho.el.Expr _caucho_expr_5;
  private static com.caucho.el.Expr _caucho_expr_6;
  private static com.caucho.el.Expr _caucho_expr_7;
  private static com.caucho.el.Expr _caucho_expr_8;
  private static com.caucho.el.Expr _caucho_expr_9;
  private static com.caucho.el.Expr _caucho_expr_10;
  private static com.caucho.el.Expr _caucho_expr_11;
  private static com.caucho.el.Expr _caucho_expr_12;
  private static com.caucho.el.Expr _caucho_expr_13;
  private static com.caucho.el.Expr _caucho_expr_14;
  private static com.caucho.el.Expr _caucho_expr_15;

  private final static char []_jsp_string4;
  private final static char []_jsp_string15;
  private final static char []_jsp_string5;
  private final static char []_jsp_string13;
  private final static char []_jsp_string14;
  private final static char []_jsp_string22;
  private final static char []_jsp_string8;
  private final static char []_jsp_string2;
  private final static char []_jsp_string7;
  private final static char []_jsp_string12;
  private final static char []_jsp_string18;
  private final static char []_jsp_string0;
  private final static char []_jsp_string10;
  private final static char []_jsp_string11;
  private final static char []_jsp_string9;
  private final static char []_jsp_string20;
  private final static char []_jsp_string6;
  private final static char []_jsp_string19;
  private final static char []_jsp_string17;
  private final static char []_jsp_string23;
  private final static char []_jsp_string1;
  private final static char []_jsp_string21;
  private final static char []_jsp_string3;
  private final static char []_jsp_string16;
  static {
    _jsp_string4 = "/js/jquery.js\"></script> \r\n		<script type=\"text/javascript\"  src=\"".toCharArray();
    _jsp_string15 = "</li>\r\n					<li>\u5ba1\u6838\u72b6\u6001\uff1a".toCharArray();
    _jsp_string5 = "/phone-js/xback.js\"></script>\r\n		<script src='".toCharArray();
    _jsp_string13 = "\r\n					</li>\r\n					<li>\r\n						\u8be6\u7ec6\u5730\u5740\uff1a".toCharArray();
    _jsp_string14 = "\r\n					</li>\r\n				</ul>\r\n			</div>\r\n			<div class=\"info-box\">	\r\n				<ul>\r\n					<li>\u63d0\u4ea4\u65f6\u95f4\uff1a".toCharArray();
    _jsp_string22 = "/portal/integral!awardDetail.action?award.id=".toCharArray();
    _jsp_string8 = "\"/>\r\n			<div class=\"info-box\">\r\n				<ul>\r\n					<li>\u59d3\u540d\uff1a".toCharArray();
    _jsp_string2 = "\r\n\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n	<head>\r\n		<title>\u79ef\u5206\u5151\u6362\u7533\u8bf7\u5355</title>\r\n		<meta name=\"keywords\" value=\"\u6210\u957f\u65e5\u5fd7\" />\r\n		<meta name=\"description\" value=\"\u9996\u4e2a\u8bb0\u5f55\u6210\u957f\u65e5\u5fd7\u7684\u7f51\u7ad9\" />\r\n		<meta name=\"author\" value=\"\u6210\u957f\u65e5\u5fd7\" />\r\n		<meta name=\"viewport\" content=\"width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;\">\r\n		<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\r\n		<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\r\n		<meta name=\"format-detection\" content=\"telephone=no\">\r\n		<link href=\"".toCharArray();
    _jsp_string7 = "/portal/integral!awardDetail.action?award.id=\"+$(\"#awardId\").val();\r\n				});\r\n		</script>\r\n	</head>\r\n	<body style=\"background-color:#F0EFEE;\">\r\n			<input type=\"hidden\" id=\"awardId\" name=\"award.id\" value=\"".toCharArray();
    _jsp_string12 = "\r\n					</li>\r\n					<li>\r\n						\u533a/\u53bf\uff1a".toCharArray();
    _jsp_string18 = "</li>\r\n				</ul>\r\n			</div>\r\n			".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string10 = "</li>\r\n				</ul>\r\n			</div>\r\n			<div class=\"info-box\">\r\n				<ul>\r\n					<li>\r\n						\u7701\uff1a".toCharArray();
    _jsp_string11 = "\r\n					</li>\r\n					<li>\r\n						\u5e02\uff1a".toCharArray();
    _jsp_string9 = "</li>\r\n					<li>\u624b\u673a\u53f7\uff1a".toCharArray();
    _jsp_string20 = "</li>\r\n					</ul>\r\n				</div>\r\n			".toCharArray();
    _jsp_string6 = "/js/hhSwipe.js' type=\"text/javascript\"></script> \r\n		<script type=\"text/javascript\">\r\n			 XBack.listen(function(){\r\n				  window.location = \"".toCharArray();
    _jsp_string19 = "\r\n				<div class=\"info-box\">	\r\n					<ul>\r\n						<li>\u53d1\u8d27\u72b6\u6001\uff1a".toCharArray();
    _jsp_string17 = "</li>\r\n					<li>\u5907\u6ce8\uff1a".toCharArray();
    _jsp_string23 = "'\">\r\n				\u8fd4&nbsp;&nbsp;&nbsp;&nbsp;\u56de\r\n			</div>\r\n	</body>\r\n</html>\r\n".toCharArray();
    _jsp_string1 = "\r\n".toCharArray();
    _jsp_string21 = "\r\n			<div class=\"sub-box\" onclick=\"window.location = '".toCharArray();
    _jsp_string3 = "/phone-css/phone.css\" rel='stylesheet' type='text/css' />\r\n		<script type=\"text/javascript\" src=\"".toCharArray();
    _jsp_string16 = "</li>\r\n					<li>\u5ba1\u6838\u65f6\u95f4\uff1a".toCharArray();
  }
}

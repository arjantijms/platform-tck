#Signature file v4.1
#Version 6.1

CLSS public abstract interface jakarta.servlet.AsyncContext
fld public final static java.lang.String ASYNC_CONTEXT_PATH = "jakarta.servlet.async.context_path"
fld public final static java.lang.String ASYNC_MAPPING = "jakarta.servlet.async.mapping"
fld public final static java.lang.String ASYNC_PATH_INFO = "jakarta.servlet.async.path_info"
fld public final static java.lang.String ASYNC_QUERY_STRING = "jakarta.servlet.async.query_string"
fld public final static java.lang.String ASYNC_REQUEST_URI = "jakarta.servlet.async.request_uri"
fld public final static java.lang.String ASYNC_SERVLET_PATH = "jakarta.servlet.async.servlet_path"
meth public abstract <%0 extends jakarta.servlet.AsyncListener> {%%0} createListener(java.lang.Class<{%%0}>) throws jakarta.servlet.ServletException
meth public abstract boolean hasOriginalRequestAndResponse()
meth public abstract jakarta.servlet.ServletRequest getRequest()
meth public abstract jakarta.servlet.ServletResponse getResponse()
meth public abstract long getTimeout()
meth public abstract void addListener(jakarta.servlet.AsyncListener)
meth public abstract void addListener(jakarta.servlet.AsyncListener,jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse)
meth public abstract void complete()
meth public abstract void dispatch()
meth public abstract void dispatch(jakarta.servlet.ServletContext,java.lang.String)
meth public abstract void dispatch(java.lang.String)
meth public abstract void setTimeout(long)
meth public abstract void start(java.lang.Runnable)

CLSS public jakarta.servlet.AsyncEvent
cons public init(jakarta.servlet.AsyncContext)
cons public init(jakarta.servlet.AsyncContext,jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse)
cons public init(jakarta.servlet.AsyncContext,jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse,java.lang.Throwable)
cons public init(jakarta.servlet.AsyncContext,java.lang.Throwable)
meth public jakarta.servlet.AsyncContext getAsyncContext()
meth public jakarta.servlet.ServletRequest getSuppliedRequest()
meth public jakarta.servlet.ServletResponse getSuppliedResponse()
meth public java.lang.Throwable getThrowable()
supr java.lang.Object
hfds context,request,response,throwable

CLSS public abstract interface jakarta.servlet.AsyncListener
intf java.util.EventListener
meth public abstract void onComplete(jakarta.servlet.AsyncEvent) throws java.io.IOException
meth public abstract void onError(jakarta.servlet.AsyncEvent) throws java.io.IOException
meth public abstract void onStartAsync(jakarta.servlet.AsyncEvent) throws java.io.IOException
meth public abstract void onTimeout(jakarta.servlet.AsyncEvent) throws java.io.IOException

CLSS public final !enum jakarta.servlet.DispatcherType
fld public final static jakarta.servlet.DispatcherType ASYNC
fld public final static jakarta.servlet.DispatcherType ERROR
fld public final static jakarta.servlet.DispatcherType FORWARD
fld public final static jakarta.servlet.DispatcherType INCLUDE
fld public final static jakarta.servlet.DispatcherType REQUEST
meth public static jakarta.servlet.DispatcherType valueOf(java.lang.String)
meth public static jakarta.servlet.DispatcherType[] values()
supr java.lang.Enum<jakarta.servlet.DispatcherType>

CLSS public abstract interface jakarta.servlet.Filter
meth public abstract void doFilter(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse,jakarta.servlet.FilterChain) throws jakarta.servlet.ServletException,java.io.IOException
meth public void destroy()
meth public void init(jakarta.servlet.FilterConfig) throws jakarta.servlet.ServletException

CLSS public abstract interface jakarta.servlet.FilterChain
meth public abstract void doFilter(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse) throws jakarta.servlet.ServletException,java.io.IOException

CLSS public abstract interface jakarta.servlet.FilterConfig
meth public abstract jakarta.servlet.ServletContext getServletContext()
meth public abstract java.lang.String getFilterName()
meth public abstract java.lang.String getInitParameter(java.lang.String)
meth public abstract java.util.Enumeration<java.lang.String> getInitParameterNames()

CLSS public abstract interface jakarta.servlet.FilterRegistration
innr public abstract interface static Dynamic
intf jakarta.servlet.Registration
meth public abstract !varargs void addMappingForServletNames(java.util.EnumSet<jakarta.servlet.DispatcherType>,boolean,java.lang.String[])
meth public abstract !varargs void addMappingForUrlPatterns(java.util.EnumSet<jakarta.servlet.DispatcherType>,boolean,java.lang.String[])
meth public abstract java.util.Collection<java.lang.String> getServletNameMappings()
meth public abstract java.util.Collection<java.lang.String> getUrlPatternMappings()

CLSS public abstract interface static jakarta.servlet.FilterRegistration$Dynamic
 outer jakarta.servlet.FilterRegistration
intf jakarta.servlet.FilterRegistration
intf jakarta.servlet.Registration$Dynamic

CLSS public abstract jakarta.servlet.GenericFilter
cons public init()
intf jakarta.servlet.Filter
intf jakarta.servlet.FilterConfig
intf java.io.Serializable
meth public jakarta.servlet.FilterConfig getFilterConfig()
meth public jakarta.servlet.ServletContext getServletContext()
meth public java.lang.String getFilterName()
meth public java.lang.String getInitParameter(java.lang.String)
meth public java.util.Enumeration<java.lang.String> getInitParameterNames()
meth public void init() throws jakarta.servlet.ServletException
meth public void init(jakarta.servlet.FilterConfig) throws jakarta.servlet.ServletException
supr java.lang.Object
hfds LSTRING_FILE,config,lStrings,serialVersionUID

CLSS public abstract jakarta.servlet.GenericServlet
cons public init()
intf jakarta.servlet.Servlet
intf jakarta.servlet.ServletConfig
intf java.io.Serializable
meth public abstract void service(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth public jakarta.servlet.ServletConfig getServletConfig()
meth public jakarta.servlet.ServletContext getServletContext()
meth public java.lang.String getInitParameter(java.lang.String)
meth public java.lang.String getServletInfo()
meth public java.lang.String getServletName()
meth public java.util.Enumeration<java.lang.String> getInitParameterNames()
meth public void destroy()
meth public void init() throws jakarta.servlet.ServletException
meth public void init(jakarta.servlet.ServletConfig) throws jakarta.servlet.ServletException
meth public void log(java.lang.String)
meth public void log(java.lang.String,java.lang.Throwable)
supr java.lang.Object
hfds LSTRING_FILE,config,lStrings,serialVersionUID

CLSS public jakarta.servlet.HttpConstraintElement
cons public !varargs init(jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic,jakarta.servlet.annotation.ServletSecurity$TransportGuarantee,java.lang.String[])
cons public !varargs init(jakarta.servlet.annotation.ServletSecurity$TransportGuarantee,java.lang.String[])
cons public init()
cons public init(jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic)
meth public jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic getEmptyRoleSemantic()
meth public jakarta.servlet.annotation.ServletSecurity$TransportGuarantee getTransportGuarantee()
meth public java.lang.String[] getRolesAllowed()
supr java.lang.Object
hfds emptyRoleSemantic,rolesAllowed,transportGuarantee

CLSS public jakarta.servlet.HttpMethodConstraintElement
cons public init(java.lang.String)
cons public init(java.lang.String,jakarta.servlet.HttpConstraintElement)
meth public java.lang.String getMethodName()
supr jakarta.servlet.HttpConstraintElement
hfds methodName

CLSS public jakarta.servlet.MultipartConfigElement
cons public init(jakarta.servlet.annotation.MultipartConfig)
cons public init(java.lang.String)
cons public init(java.lang.String,long,long,int)
meth public int getFileSizeThreshold()
meth public java.lang.String getLocation()
meth public long getMaxFileSize()
meth public long getMaxRequestSize()
supr java.lang.Object
hfds fileSizeThreshold,location,maxFileSize,maxRequestSize

CLSS public abstract interface jakarta.servlet.ReadListener
intf java.util.EventListener
meth public abstract void onAllDataRead() throws java.io.IOException
meth public abstract void onDataAvailable() throws java.io.IOException
meth public abstract void onError(java.lang.Throwable)

CLSS public abstract interface jakarta.servlet.Registration
innr public abstract interface static Dynamic
meth public abstract boolean setInitParameter(java.lang.String,java.lang.String)
meth public abstract java.lang.String getClassName()
meth public abstract java.lang.String getInitParameter(java.lang.String)
meth public abstract java.lang.String getName()
meth public abstract java.util.Map<java.lang.String,java.lang.String> getInitParameters()
meth public abstract java.util.Set<java.lang.String> setInitParameters(java.util.Map<java.lang.String,java.lang.String>)

CLSS public abstract interface static jakarta.servlet.Registration$Dynamic
 outer jakarta.servlet.Registration
intf jakarta.servlet.Registration
meth public abstract void setAsyncSupported(boolean)

CLSS public abstract interface jakarta.servlet.RequestDispatcher
fld public final static java.lang.String ERROR_EXCEPTION = "jakarta.servlet.error.exception"
fld public final static java.lang.String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type"
fld public final static java.lang.String ERROR_MESSAGE = "jakarta.servlet.error.message"
fld public final static java.lang.String ERROR_METHOD = "jakarta.servlet.error.method"
fld public final static java.lang.String ERROR_QUERY_STRING = "jakarta.servlet.error.query_string"
fld public final static java.lang.String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri"
fld public final static java.lang.String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name"
fld public final static java.lang.String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code"
fld public final static java.lang.String FORWARD_CONTEXT_PATH = "jakarta.servlet.forward.context_path"
fld public final static java.lang.String FORWARD_MAPPING = "jakarta.servlet.forward.mapping"
fld public final static java.lang.String FORWARD_PATH_INFO = "jakarta.servlet.forward.path_info"
fld public final static java.lang.String FORWARD_QUERY_STRING = "jakarta.servlet.forward.query_string"
fld public final static java.lang.String FORWARD_REQUEST_URI = "jakarta.servlet.forward.request_uri"
fld public final static java.lang.String FORWARD_SERVLET_PATH = "jakarta.servlet.forward.servlet_path"
fld public final static java.lang.String INCLUDE_CONTEXT_PATH = "jakarta.servlet.include.context_path"
fld public final static java.lang.String INCLUDE_MAPPING = "jakarta.servlet.include.mapping"
fld public final static java.lang.String INCLUDE_PATH_INFO = "jakarta.servlet.include.path_info"
fld public final static java.lang.String INCLUDE_QUERY_STRING = "jakarta.servlet.include.query_string"
fld public final static java.lang.String INCLUDE_REQUEST_URI = "jakarta.servlet.include.request_uri"
fld public final static java.lang.String INCLUDE_SERVLET_PATH = "jakarta.servlet.include.servlet_path"
meth public abstract void forward(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth public abstract void include(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse) throws jakarta.servlet.ServletException,java.io.IOException

CLSS public abstract interface jakarta.servlet.Servlet
meth public abstract jakarta.servlet.ServletConfig getServletConfig()
meth public abstract java.lang.String getServletInfo()
meth public abstract void destroy()
meth public abstract void init(jakarta.servlet.ServletConfig) throws jakarta.servlet.ServletException
meth public abstract void service(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse) throws jakarta.servlet.ServletException,java.io.IOException

CLSS public abstract interface jakarta.servlet.ServletConfig
meth public abstract jakarta.servlet.ServletContext getServletContext()
meth public abstract java.lang.String getInitParameter(java.lang.String)
meth public abstract java.lang.String getServletName()
meth public abstract java.util.Enumeration<java.lang.String> getInitParameterNames()

CLSS public abstract interface jakarta.servlet.ServletConnection
meth public abstract boolean isSecure()
meth public abstract java.lang.String getConnectionId()
meth public abstract java.lang.String getProtocol()
meth public abstract java.lang.String getProtocolConnectionId()

CLSS public abstract interface jakarta.servlet.ServletContainerInitializer
meth public abstract void onStartup(java.util.Set<java.lang.Class<?>>,jakarta.servlet.ServletContext) throws jakarta.servlet.ServletException

CLSS public abstract interface jakarta.servlet.ServletContext
fld public final static java.lang.String ORDERED_LIBS = "jakarta.servlet.context.orderedLibs"
fld public final static java.lang.String TEMPDIR = "jakarta.servlet.context.tempdir"
meth public abstract !varargs void declareRoles(java.lang.String[])
meth public abstract <%0 extends jakarta.servlet.Filter> {%%0} createFilter(java.lang.Class<{%%0}>) throws jakarta.servlet.ServletException
meth public abstract <%0 extends jakarta.servlet.Servlet> {%%0} createServlet(java.lang.Class<{%%0}>) throws jakarta.servlet.ServletException
meth public abstract <%0 extends java.util.EventListener> void addListener({%%0})
meth public abstract <%0 extends java.util.EventListener> {%%0} createListener(java.lang.Class<{%%0}>) throws jakarta.servlet.ServletException
meth public abstract boolean setInitParameter(java.lang.String,java.lang.String)
meth public abstract int getEffectiveMajorVersion()
meth public abstract int getEffectiveMinorVersion()
meth public abstract int getMajorVersion()
meth public abstract int getMinorVersion()
meth public abstract int getSessionTimeout()
meth public abstract jakarta.servlet.FilterRegistration getFilterRegistration(java.lang.String)
meth public abstract jakarta.servlet.FilterRegistration$Dynamic addFilter(java.lang.String,jakarta.servlet.Filter)
meth public abstract jakarta.servlet.FilterRegistration$Dynamic addFilter(java.lang.String,java.lang.Class<? extends jakarta.servlet.Filter>)
meth public abstract jakarta.servlet.FilterRegistration$Dynamic addFilter(java.lang.String,java.lang.String)
meth public abstract jakarta.servlet.RequestDispatcher getNamedDispatcher(java.lang.String)
meth public abstract jakarta.servlet.RequestDispatcher getRequestDispatcher(java.lang.String)
meth public abstract jakarta.servlet.ServletContext getContext(java.lang.String)
meth public abstract jakarta.servlet.ServletRegistration getServletRegistration(java.lang.String)
meth public abstract jakarta.servlet.ServletRegistration$Dynamic addJspFile(java.lang.String,java.lang.String)
meth public abstract jakarta.servlet.ServletRegistration$Dynamic addServlet(java.lang.String,jakarta.servlet.Servlet)
meth public abstract jakarta.servlet.ServletRegistration$Dynamic addServlet(java.lang.String,java.lang.Class<? extends jakarta.servlet.Servlet>)
meth public abstract jakarta.servlet.ServletRegistration$Dynamic addServlet(java.lang.String,java.lang.String)
meth public abstract jakarta.servlet.SessionCookieConfig getSessionCookieConfig()
meth public abstract jakarta.servlet.descriptor.JspConfigDescriptor getJspConfigDescriptor()
meth public abstract java.io.InputStream getResourceAsStream(java.lang.String)
meth public abstract java.lang.ClassLoader getClassLoader()
meth public abstract java.lang.Object getAttribute(java.lang.String)
meth public abstract java.lang.String getContextPath()
meth public abstract java.lang.String getInitParameter(java.lang.String)
meth public abstract java.lang.String getMimeType(java.lang.String)
meth public abstract java.lang.String getRealPath(java.lang.String)
meth public abstract java.lang.String getRequestCharacterEncoding()
meth public abstract java.lang.String getResponseCharacterEncoding()
meth public abstract java.lang.String getServerInfo()
meth public abstract java.lang.String getServletContextName()
meth public abstract java.lang.String getVirtualServerName()
meth public abstract java.net.URL getResource(java.lang.String) throws java.net.MalformedURLException
meth public abstract java.util.Enumeration<java.lang.String> getAttributeNames()
meth public abstract java.util.Enumeration<java.lang.String> getInitParameterNames()
meth public abstract java.util.Map<java.lang.String,? extends jakarta.servlet.FilterRegistration> getFilterRegistrations()
meth public abstract java.util.Map<java.lang.String,? extends jakarta.servlet.ServletRegistration> getServletRegistrations()
meth public abstract java.util.Set<jakarta.servlet.SessionTrackingMode> getDefaultSessionTrackingModes()
meth public abstract java.util.Set<jakarta.servlet.SessionTrackingMode> getEffectiveSessionTrackingModes()
meth public abstract java.util.Set<java.lang.String> getResourcePaths(java.lang.String)
meth public abstract void addListener(java.lang.Class<? extends java.util.EventListener>)
meth public abstract void addListener(java.lang.String)
meth public abstract void log(java.lang.String)
meth public abstract void log(java.lang.String,java.lang.Throwable)
meth public abstract void removeAttribute(java.lang.String)
meth public abstract void setAttribute(java.lang.String,java.lang.Object)
meth public abstract void setRequestCharacterEncoding(java.lang.String)
meth public abstract void setResponseCharacterEncoding(java.lang.String)
meth public abstract void setSessionTimeout(int)
meth public abstract void setSessionTrackingModes(java.util.Set<jakarta.servlet.SessionTrackingMode>)
meth public void setRequestCharacterEncoding(java.nio.charset.Charset)
meth public void setResponseCharacterEncoding(java.nio.charset.Charset)

CLSS public jakarta.servlet.ServletContextAttributeEvent
cons public init(jakarta.servlet.ServletContext,java.lang.String,java.lang.Object)
meth public java.lang.Object getValue()
meth public java.lang.String getName()
supr jakarta.servlet.ServletContextEvent
hfds name,serialVersionUID,value

CLSS public abstract interface jakarta.servlet.ServletContextAttributeListener
intf java.util.EventListener
meth public void attributeAdded(jakarta.servlet.ServletContextAttributeEvent)
meth public void attributeRemoved(jakarta.servlet.ServletContextAttributeEvent)
meth public void attributeReplaced(jakarta.servlet.ServletContextAttributeEvent)

CLSS public jakarta.servlet.ServletContextEvent
cons public init(jakarta.servlet.ServletContext)
meth public jakarta.servlet.ServletContext getServletContext()
supr java.util.EventObject
hfds serialVersionUID

CLSS public abstract interface jakarta.servlet.ServletContextListener
intf java.util.EventListener
meth public void contextDestroyed(jakarta.servlet.ServletContextEvent)
meth public void contextInitialized(jakarta.servlet.ServletContextEvent)

CLSS public jakarta.servlet.ServletException
cons public init()
cons public init(java.lang.String)
cons public init(java.lang.String,java.lang.Throwable)
cons public init(java.lang.Throwable)
meth public java.lang.Throwable getRootCause()
supr java.lang.Exception
hfds rootCause,serialVersionUID

CLSS public abstract jakarta.servlet.ServletInputStream
cons protected init()
meth public abstract boolean isFinished()
meth public abstract boolean isReady()
meth public abstract void setReadListener(jakarta.servlet.ReadListener)
meth public byte[] readAllBytes() throws java.io.IOException
meth public byte[] readNBytes(int) throws java.io.IOException
meth public int read(java.nio.ByteBuffer) throws java.io.IOException
meth public int readLine(byte[],int,int) throws java.io.IOException
meth public int readNBytes(byte[],int,int) throws java.io.IOException
supr java.io.InputStream

CLSS public abstract jakarta.servlet.ServletOutputStream
cons protected init()
meth public abstract boolean isReady()
meth public abstract void setWriteListener(jakarta.servlet.WriteListener)
meth public void close() throws java.io.IOException
meth public void print(boolean) throws java.io.IOException
meth public void print(char) throws java.io.IOException
meth public void print(double) throws java.io.IOException
meth public void print(float) throws java.io.IOException
meth public void print(int) throws java.io.IOException
meth public void print(java.lang.String) throws java.io.IOException
meth public void print(long) throws java.io.IOException
meth public void println() throws java.io.IOException
meth public void println(boolean) throws java.io.IOException
meth public void println(char) throws java.io.IOException
meth public void println(double) throws java.io.IOException
meth public void println(float) throws java.io.IOException
meth public void println(int) throws java.io.IOException
meth public void println(java.lang.String) throws java.io.IOException
meth public void println(long) throws java.io.IOException
meth public void write(java.nio.ByteBuffer) throws java.io.IOException
supr java.io.OutputStream
hfds LSTRING_FILE,lStrings

CLSS public abstract interface jakarta.servlet.ServletRegistration
innr public abstract interface static Dynamic
intf jakarta.servlet.Registration
meth public abstract !varargs java.util.Set<java.lang.String> addMapping(java.lang.String[])
meth public abstract java.lang.String getRunAsRole()
meth public abstract java.util.Collection<java.lang.String> getMappings()

CLSS public abstract interface static jakarta.servlet.ServletRegistration$Dynamic
 outer jakarta.servlet.ServletRegistration
intf jakarta.servlet.Registration$Dynamic
intf jakarta.servlet.ServletRegistration
meth public abstract java.util.Set<java.lang.String> setServletSecurity(jakarta.servlet.ServletSecurityElement)
meth public abstract void setLoadOnStartup(int)
meth public abstract void setMultipartConfig(jakarta.servlet.MultipartConfigElement)
meth public abstract void setRunAsRole(java.lang.String)

CLSS public abstract interface jakarta.servlet.ServletRequest
meth public abstract boolean isAsyncStarted()
meth public abstract boolean isAsyncSupported()
meth public abstract boolean isSecure()
meth public abstract int getContentLength()
meth public abstract int getLocalPort()
meth public abstract int getRemotePort()
meth public abstract int getServerPort()
meth public abstract jakarta.servlet.AsyncContext getAsyncContext()
meth public abstract jakarta.servlet.AsyncContext startAsync()
meth public abstract jakarta.servlet.AsyncContext startAsync(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse)
meth public abstract jakarta.servlet.DispatcherType getDispatcherType()
meth public abstract jakarta.servlet.RequestDispatcher getRequestDispatcher(java.lang.String)
meth public abstract jakarta.servlet.ServletConnection getServletConnection()
meth public abstract jakarta.servlet.ServletContext getServletContext()
meth public abstract jakarta.servlet.ServletInputStream getInputStream() throws java.io.IOException
meth public abstract java.io.BufferedReader getReader() throws java.io.IOException
meth public abstract java.lang.Object getAttribute(java.lang.String)
meth public abstract java.lang.String getCharacterEncoding()
meth public abstract java.lang.String getContentType()
meth public abstract java.lang.String getLocalAddr()
meth public abstract java.lang.String getLocalName()
meth public abstract java.lang.String getParameter(java.lang.String)
meth public abstract java.lang.String getProtocol()
meth public abstract java.lang.String getProtocolRequestId()
meth public abstract java.lang.String getRemoteAddr()
meth public abstract java.lang.String getRemoteHost()
meth public abstract java.lang.String getRequestId()
meth public abstract java.lang.String getScheme()
meth public abstract java.lang.String getServerName()
meth public abstract java.lang.String[] getParameterValues(java.lang.String)
meth public abstract java.util.Enumeration<java.lang.String> getAttributeNames()
meth public abstract java.util.Enumeration<java.lang.String> getParameterNames()
meth public abstract java.util.Enumeration<java.util.Locale> getLocales()
meth public abstract java.util.Locale getLocale()
meth public abstract java.util.Map<java.lang.String,java.lang.String[]> getParameterMap()
meth public abstract long getContentLengthLong()
meth public abstract void removeAttribute(java.lang.String)
meth public abstract void setAttribute(java.lang.String,java.lang.Object)
meth public abstract void setCharacterEncoding(java.lang.String) throws java.io.UnsupportedEncodingException
meth public void setCharacterEncoding(java.nio.charset.Charset)

CLSS public jakarta.servlet.ServletRequestAttributeEvent
cons public init(jakarta.servlet.ServletContext,jakarta.servlet.ServletRequest,java.lang.String,java.lang.Object)
meth public java.lang.Object getValue()
meth public java.lang.String getName()
supr jakarta.servlet.ServletRequestEvent
hfds name,serialVersionUID,value

CLSS public abstract interface jakarta.servlet.ServletRequestAttributeListener
intf java.util.EventListener
meth public void attributeAdded(jakarta.servlet.ServletRequestAttributeEvent)
meth public void attributeRemoved(jakarta.servlet.ServletRequestAttributeEvent)
meth public void attributeReplaced(jakarta.servlet.ServletRequestAttributeEvent)

CLSS public jakarta.servlet.ServletRequestEvent
cons public init(jakarta.servlet.ServletContext,jakarta.servlet.ServletRequest)
meth public jakarta.servlet.ServletContext getServletContext()
meth public jakarta.servlet.ServletRequest getServletRequest()
supr java.util.EventObject
hfds request,serialVersionUID

CLSS public abstract interface jakarta.servlet.ServletRequestListener
intf java.util.EventListener
meth public void requestDestroyed(jakarta.servlet.ServletRequestEvent)
meth public void requestInitialized(jakarta.servlet.ServletRequestEvent)

CLSS public jakarta.servlet.ServletRequestWrapper
cons public init(jakarta.servlet.ServletRequest)
intf jakarta.servlet.ServletRequest
meth public boolean isAsyncStarted()
meth public boolean isAsyncSupported()
meth public boolean isSecure()
meth public boolean isWrapperFor(jakarta.servlet.ServletRequest)
meth public boolean isWrapperFor(java.lang.Class<?>)
meth public int getContentLength()
meth public int getLocalPort()
meth public int getRemotePort()
meth public int getServerPort()
meth public jakarta.servlet.AsyncContext getAsyncContext()
meth public jakarta.servlet.AsyncContext startAsync()
meth public jakarta.servlet.AsyncContext startAsync(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse)
meth public jakarta.servlet.DispatcherType getDispatcherType()
meth public jakarta.servlet.RequestDispatcher getRequestDispatcher(java.lang.String)
meth public jakarta.servlet.ServletConnection getServletConnection()
meth public jakarta.servlet.ServletContext getServletContext()
meth public jakarta.servlet.ServletInputStream getInputStream() throws java.io.IOException
meth public jakarta.servlet.ServletRequest getRequest()
meth public java.io.BufferedReader getReader() throws java.io.IOException
meth public java.lang.Object getAttribute(java.lang.String)
meth public java.lang.String getCharacterEncoding()
meth public java.lang.String getContentType()
meth public java.lang.String getLocalAddr()
meth public java.lang.String getLocalName()
meth public java.lang.String getParameter(java.lang.String)
meth public java.lang.String getProtocol()
meth public java.lang.String getProtocolRequestId()
meth public java.lang.String getRemoteAddr()
meth public java.lang.String getRemoteHost()
meth public java.lang.String getRequestId()
meth public java.lang.String getScheme()
meth public java.lang.String getServerName()
meth public java.lang.String[] getParameterValues(java.lang.String)
meth public java.util.Enumeration<java.lang.String> getAttributeNames()
meth public java.util.Enumeration<java.lang.String> getParameterNames()
meth public java.util.Enumeration<java.util.Locale> getLocales()
meth public java.util.Locale getLocale()
meth public java.util.Map<java.lang.String,java.lang.String[]> getParameterMap()
meth public long getContentLengthLong()
meth public void removeAttribute(java.lang.String)
meth public void setAttribute(java.lang.String,java.lang.Object)
meth public void setCharacterEncoding(java.lang.String) throws java.io.UnsupportedEncodingException
meth public void setCharacterEncoding(java.nio.charset.Charset)
meth public void setRequest(jakarta.servlet.ServletRequest)
supr java.lang.Object
hfds request

CLSS public abstract interface jakarta.servlet.ServletResponse
meth public abstract boolean isCommitted()
meth public abstract int getBufferSize()
meth public abstract jakarta.servlet.ServletOutputStream getOutputStream() throws java.io.IOException
meth public abstract java.io.PrintWriter getWriter() throws java.io.IOException
meth public abstract java.lang.String getCharacterEncoding()
meth public abstract java.lang.String getContentType()
meth public abstract java.util.Locale getLocale()
meth public abstract void flushBuffer() throws java.io.IOException
meth public abstract void reset()
meth public abstract void resetBuffer()
meth public abstract void setBufferSize(int)
meth public abstract void setCharacterEncoding(java.lang.String)
meth public abstract void setContentLength(int)
meth public abstract void setContentLengthLong(long)
meth public abstract void setContentType(java.lang.String)
meth public abstract void setLocale(java.util.Locale)
meth public void setCharacterEncoding(java.nio.charset.Charset)

CLSS public jakarta.servlet.ServletResponseWrapper
cons public init(jakarta.servlet.ServletResponse)
intf jakarta.servlet.ServletResponse
meth public boolean isCommitted()
meth public boolean isWrapperFor(jakarta.servlet.ServletResponse)
meth public boolean isWrapperFor(java.lang.Class<?>)
meth public int getBufferSize()
meth public jakarta.servlet.ServletOutputStream getOutputStream() throws java.io.IOException
meth public jakarta.servlet.ServletResponse getResponse()
meth public java.io.PrintWriter getWriter() throws java.io.IOException
meth public java.lang.String getCharacterEncoding()
meth public java.lang.String getContentType()
meth public java.util.Locale getLocale()
meth public void flushBuffer() throws java.io.IOException
meth public void reset()
meth public void resetBuffer()
meth public void setBufferSize(int)
meth public void setCharacterEncoding(java.lang.String)
meth public void setCharacterEncoding(java.nio.charset.Charset)
meth public void setContentLength(int)
meth public void setContentLengthLong(long)
meth public void setContentType(java.lang.String)
meth public void setLocale(java.util.Locale)
meth public void setResponse(jakarta.servlet.ServletResponse)
supr java.lang.Object
hfds response

CLSS public jakarta.servlet.ServletSecurityElement
cons public init()
cons public init(jakarta.servlet.HttpConstraintElement)
cons public init(jakarta.servlet.HttpConstraintElement,java.util.Collection<jakarta.servlet.HttpMethodConstraintElement>)
cons public init(jakarta.servlet.annotation.ServletSecurity)
cons public init(java.util.Collection<jakarta.servlet.HttpMethodConstraintElement>)
meth public java.util.Collection<jakarta.servlet.HttpMethodConstraintElement> getHttpMethodConstraints()
meth public java.util.Collection<java.lang.String> getMethodNames()
supr jakarta.servlet.HttpConstraintElement
hfds methodConstraints,methodNames

CLSS public abstract interface jakarta.servlet.SessionCookieConfig
meth public abstract boolean isHttpOnly()
meth public abstract boolean isSecure()
meth public abstract int getMaxAge()
meth public abstract java.lang.String getAttribute(java.lang.String)
meth public abstract java.lang.String getComment()
 anno 0 java.lang.Deprecated(boolean forRemoval=true, java.lang.String since="Servlet 6.0")
meth public abstract java.lang.String getDomain()
meth public abstract java.lang.String getName()
meth public abstract java.lang.String getPath()
meth public abstract java.util.Map<java.lang.String,java.lang.String> getAttributes()
meth public abstract void setAttribute(java.lang.String,java.lang.String)
meth public abstract void setComment(java.lang.String)
 anno 0 java.lang.Deprecated(boolean forRemoval=true, java.lang.String since="Servlet 6.0")
meth public abstract void setDomain(java.lang.String)
meth public abstract void setHttpOnly(boolean)
meth public abstract void setMaxAge(int)
meth public abstract void setName(java.lang.String)
meth public abstract void setPath(java.lang.String)
meth public abstract void setSecure(boolean)

CLSS public final !enum jakarta.servlet.SessionTrackingMode
fld public final static jakarta.servlet.SessionTrackingMode COOKIE
fld public final static jakarta.servlet.SessionTrackingMode SSL
fld public final static jakarta.servlet.SessionTrackingMode URL
meth public static jakarta.servlet.SessionTrackingMode valueOf(java.lang.String)
meth public static jakarta.servlet.SessionTrackingMode[] values()
supr java.lang.Enum<jakarta.servlet.SessionTrackingMode>

CLSS public jakarta.servlet.UnavailableException
cons public init(java.lang.String)
cons public init(java.lang.String,int)
meth public boolean isPermanent()
meth public int getUnavailableSeconds()
supr jakarta.servlet.ServletException
hfds permanent,seconds,serialVersionUID

CLSS public abstract interface jakarta.servlet.WriteListener
intf java.util.EventListener
meth public abstract void onError(java.lang.Throwable)
meth public abstract void onWritePossible() throws java.io.IOException

CLSS public abstract interface !annotation jakarta.servlet.annotation.HandlesTypes
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[TYPE])
intf java.lang.annotation.Annotation
meth public abstract java.lang.Class<?>[] value()

CLSS public abstract interface !annotation jakarta.servlet.annotation.HttpConstraint
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
intf java.lang.annotation.Annotation
meth public abstract !hasdefault jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic value()
meth public abstract !hasdefault jakarta.servlet.annotation.ServletSecurity$TransportGuarantee transportGuarantee()
meth public abstract !hasdefault java.lang.String[] rolesAllowed()

CLSS public abstract interface !annotation jakarta.servlet.annotation.HttpMethodConstraint
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
intf java.lang.annotation.Annotation
meth public abstract !hasdefault jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic emptyRoleSemantic()
meth public abstract !hasdefault jakarta.servlet.annotation.ServletSecurity$TransportGuarantee transportGuarantee()
meth public abstract !hasdefault java.lang.String[] rolesAllowed()
meth public abstract java.lang.String value()

CLSS public abstract interface !annotation jakarta.servlet.annotation.MultipartConfig
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[TYPE])
intf java.lang.annotation.Annotation
meth public abstract !hasdefault int fileSizeThreshold()
meth public abstract !hasdefault java.lang.String location()
meth public abstract !hasdefault long maxFileSize()
meth public abstract !hasdefault long maxRequestSize()

CLSS public abstract interface !annotation jakarta.servlet.annotation.ServletSecurity
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Inherited()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[TYPE])
innr public final static !enum EmptyRoleSemantic
innr public final static !enum TransportGuarantee
intf java.lang.annotation.Annotation
meth public abstract !hasdefault jakarta.servlet.annotation.HttpConstraint value()
meth public abstract !hasdefault jakarta.servlet.annotation.HttpMethodConstraint[] httpMethodConstraints()

CLSS public final static !enum jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic
 outer jakarta.servlet.annotation.ServletSecurity
fld public final static jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic DENY
fld public final static jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic PERMIT
meth public static jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic valueOf(java.lang.String)
meth public static jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic[] values()
supr java.lang.Enum<jakarta.servlet.annotation.ServletSecurity$EmptyRoleSemantic>

CLSS public final static !enum jakarta.servlet.annotation.ServletSecurity$TransportGuarantee
 outer jakarta.servlet.annotation.ServletSecurity
fld public final static jakarta.servlet.annotation.ServletSecurity$TransportGuarantee CONFIDENTIAL
fld public final static jakarta.servlet.annotation.ServletSecurity$TransportGuarantee NONE
meth public static jakarta.servlet.annotation.ServletSecurity$TransportGuarantee valueOf(java.lang.String)
meth public static jakarta.servlet.annotation.ServletSecurity$TransportGuarantee[] values()
supr java.lang.Enum<jakarta.servlet.annotation.ServletSecurity$TransportGuarantee>

CLSS public abstract interface !annotation jakarta.servlet.annotation.WebFilter
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[TYPE])
intf java.lang.annotation.Annotation
meth public abstract !hasdefault boolean asyncSupported()
meth public abstract !hasdefault jakarta.servlet.DispatcherType[] dispatcherTypes()
meth public abstract !hasdefault jakarta.servlet.annotation.WebInitParam[] initParams()
meth public abstract !hasdefault java.lang.String description()
meth public abstract !hasdefault java.lang.String displayName()
meth public abstract !hasdefault java.lang.String filterName()
meth public abstract !hasdefault java.lang.String largeIcon()
meth public abstract !hasdefault java.lang.String smallIcon()
meth public abstract !hasdefault java.lang.String[] servletNames()
meth public abstract !hasdefault java.lang.String[] urlPatterns()
meth public abstract !hasdefault java.lang.String[] value()

CLSS public abstract interface !annotation jakarta.servlet.annotation.WebInitParam
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[TYPE])
intf java.lang.annotation.Annotation
meth public abstract !hasdefault java.lang.String description()
meth public abstract java.lang.String name()
meth public abstract java.lang.String value()

CLSS public abstract interface !annotation jakarta.servlet.annotation.WebListener
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[TYPE])
intf java.lang.annotation.Annotation
meth public abstract !hasdefault java.lang.String value()

CLSS public abstract interface !annotation jakarta.servlet.annotation.WebServlet
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[TYPE])
intf java.lang.annotation.Annotation
meth public abstract !hasdefault boolean asyncSupported()
meth public abstract !hasdefault int loadOnStartup()
meth public abstract !hasdefault jakarta.servlet.annotation.WebInitParam[] initParams()
meth public abstract !hasdefault java.lang.String description()
meth public abstract !hasdefault java.lang.String displayName()
meth public abstract !hasdefault java.lang.String largeIcon()
meth public abstract !hasdefault java.lang.String name()
meth public abstract !hasdefault java.lang.String smallIcon()
meth public abstract !hasdefault java.lang.String[] urlPatterns()
meth public abstract !hasdefault java.lang.String[] value()

CLSS public abstract interface jakarta.servlet.descriptor.JspConfigDescriptor
meth public abstract java.util.Collection<jakarta.servlet.descriptor.JspPropertyGroupDescriptor> getJspPropertyGroups()
meth public abstract java.util.Collection<jakarta.servlet.descriptor.TaglibDescriptor> getTaglibs()

CLSS public abstract interface jakarta.servlet.descriptor.JspPropertyGroupDescriptor
meth public abstract java.lang.String getBuffer()
meth public abstract java.lang.String getDefaultContentType()
meth public abstract java.lang.String getDeferredSyntaxAllowedAsLiteral()
meth public abstract java.lang.String getElIgnored()
meth public abstract java.lang.String getErrorOnELNotFound()
meth public abstract java.lang.String getErrorOnUndeclaredNamespace()
meth public abstract java.lang.String getIsXml()
meth public abstract java.lang.String getPageEncoding()
meth public abstract java.lang.String getScriptingInvalid()
meth public abstract java.lang.String getTrimDirectiveWhitespaces()
meth public abstract java.util.Collection<java.lang.String> getIncludeCodas()
meth public abstract java.util.Collection<java.lang.String> getIncludePreludes()
meth public abstract java.util.Collection<java.lang.String> getUrlPatterns()

CLSS public abstract interface jakarta.servlet.descriptor.TaglibDescriptor
meth public abstract java.lang.String getTaglibLocation()
meth public abstract java.lang.String getTaglibURI()

CLSS public jakarta.servlet.http.Cookie
cons public init(java.lang.String,java.lang.String)
intf java.io.Serializable
intf java.lang.Cloneable
meth public boolean equals(java.lang.Object)
meth public boolean getSecure()
meth public boolean isHttpOnly()
meth public int getMaxAge()
meth public int getVersion()
 anno 0 java.lang.Deprecated(boolean forRemoval=true, java.lang.String since="Servlet 6.0")
meth public int hashCode()
meth public java.lang.Object clone()
meth public java.lang.String getAttribute(java.lang.String)
meth public java.lang.String getComment()
 anno 0 java.lang.Deprecated(boolean forRemoval=true, java.lang.String since="Servlet 6.0")
meth public java.lang.String getDomain()
meth public java.lang.String getName()
meth public java.lang.String getPath()
meth public java.lang.String getValue()
meth public java.lang.String toString()
meth public java.util.Map<java.lang.String,java.lang.String> getAttributes()
meth public void setAttribute(java.lang.String,java.lang.String)
meth public void setComment(java.lang.String)
 anno 0 java.lang.Deprecated(boolean forRemoval=true, java.lang.String since="Servlet 6.0")
meth public void setDomain(java.lang.String)
meth public void setHttpOnly(boolean)
meth public void setMaxAge(int)
meth public void setPath(java.lang.String)
meth public void setSecure(boolean)
meth public void setValue(java.lang.String)
meth public void setVersion(int)
 anno 0 java.lang.Deprecated(boolean forRemoval=true, java.lang.String since="Servlet 6.0")
supr java.lang.Object
hfds DOMAIN,EMPTY_STRING,HTTP_ONLY,LSTRING_FILE,MAX_AGE,PATH,SECURE,TSPECIALS,attributes,lStrings,name,serialVersionUID,value

CLSS public abstract jakarta.servlet.http.HttpFilter
cons public init()
meth protected void doFilter(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse,jakarta.servlet.FilterChain) throws jakarta.servlet.ServletException,java.io.IOException
meth public void doFilter(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse,jakarta.servlet.FilterChain) throws jakarta.servlet.ServletException,java.io.IOException
supr jakarta.servlet.GenericFilter
hfds serialVersionUID

CLSS public abstract jakarta.servlet.http.HttpServlet
cons public init()
fld public final static java.lang.String LEGACY_DO_HEAD = "jakarta.servlet.http.legacyDoHead"
 anno 0 java.lang.Deprecated(boolean forRemoval=true, java.lang.String since="Servlet 6.0")
meth protected boolean isSensitiveHeader(java.lang.String)
meth protected long getLastModified(jakarta.servlet.http.HttpServletRequest)
meth protected void doDelete(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth protected void doGet(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth protected void doHead(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth protected void doOptions(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth protected void doPatch(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth protected void doPost(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth protected void doPut(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth protected void doTrace(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth protected void service(jakarta.servlet.http.HttpServletRequest,jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth public void init(jakarta.servlet.ServletConfig) throws jakarta.servlet.ServletException
meth public void service(jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
supr jakarta.servlet.GenericServlet
hfds HEADER_IFMODSINCE,HEADER_LASTMOD,LSTRING_FILE,METHOD_DELETE,METHOD_GET,METHOD_HEAD,METHOD_OPTIONS,METHOD_PATCH,METHOD_POST,METHOD_PUT,METHOD_TRACE,SENSITIVE_HTTP_HEADERS,lStrings,legacyHeadHandling,serialVersionUID

CLSS public abstract interface jakarta.servlet.http.HttpServletMapping
meth public abstract jakarta.servlet.http.MappingMatch getMappingMatch()
meth public abstract java.lang.String getMatchValue()
meth public abstract java.lang.String getPattern()
meth public abstract java.lang.String getServletName()

CLSS public abstract interface jakarta.servlet.http.HttpServletRequest
fld public final static java.lang.String BASIC_AUTH = "BASIC"
fld public final static java.lang.String CLIENT_CERT_AUTH = "CLIENT_CERT"
fld public final static java.lang.String DIGEST_AUTH = "DIGEST"
fld public final static java.lang.String FORM_AUTH = "FORM"
intf jakarta.servlet.ServletRequest
meth public abstract <%0 extends jakarta.servlet.http.HttpUpgradeHandler> {%%0} upgrade(java.lang.Class<{%%0}>) throws jakarta.servlet.ServletException,java.io.IOException
meth public abstract boolean authenticate(jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth public abstract boolean isRequestedSessionIdFromCookie()
meth public abstract boolean isRequestedSessionIdFromURL()
meth public abstract boolean isRequestedSessionIdValid()
meth public abstract boolean isUserInRole(java.lang.String)
meth public abstract int getIntHeader(java.lang.String)
meth public abstract jakarta.servlet.http.Cookie[] getCookies()
meth public abstract jakarta.servlet.http.HttpSession getSession()
meth public abstract jakarta.servlet.http.HttpSession getSession(boolean)
meth public abstract jakarta.servlet.http.Part getPart(java.lang.String) throws jakarta.servlet.ServletException,java.io.IOException
meth public abstract java.lang.String changeSessionId()
meth public abstract java.lang.String getAuthType()
meth public abstract java.lang.String getContextPath()
meth public abstract java.lang.String getHeader(java.lang.String)
meth public abstract java.lang.String getMethod()
meth public abstract java.lang.String getPathInfo()
meth public abstract java.lang.String getPathTranslated()
meth public abstract java.lang.String getQueryString()
meth public abstract java.lang.String getRemoteUser()
meth public abstract java.lang.String getRequestURI()
meth public abstract java.lang.String getRequestedSessionId()
meth public abstract java.lang.String getServletPath()
meth public abstract java.lang.StringBuffer getRequestURL()
meth public abstract java.security.Principal getUserPrincipal()
meth public abstract java.util.Collection<jakarta.servlet.http.Part> getParts() throws jakarta.servlet.ServletException,java.io.IOException
meth public abstract java.util.Enumeration<java.lang.String> getHeaderNames()
meth public abstract java.util.Enumeration<java.lang.String> getHeaders(java.lang.String)
meth public abstract long getDateHeader(java.lang.String)
meth public abstract void login(java.lang.String,java.lang.String) throws jakarta.servlet.ServletException
meth public abstract void logout() throws jakarta.servlet.ServletException
meth public boolean isTrailerFieldsReady()
meth public jakarta.servlet.http.HttpServletMapping getHttpServletMapping()
meth public jakarta.servlet.http.PushBuilder newPushBuilder()
 anno 0 java.lang.Deprecated(boolean forRemoval=false, java.lang.String since="")
meth public java.util.Map<java.lang.String,java.lang.String> getTrailerFields()

CLSS public jakarta.servlet.http.HttpServletRequestWrapper
cons public init(jakarta.servlet.http.HttpServletRequest)
intf jakarta.servlet.http.HttpServletRequest
meth public <%0 extends jakarta.servlet.http.HttpUpgradeHandler> {%%0} upgrade(java.lang.Class<{%%0}>) throws jakarta.servlet.ServletException,java.io.IOException
meth public boolean authenticate(jakarta.servlet.http.HttpServletResponse) throws jakarta.servlet.ServletException,java.io.IOException
meth public boolean isRequestedSessionIdFromCookie()
meth public boolean isRequestedSessionIdFromURL()
meth public boolean isRequestedSessionIdValid()
meth public boolean isTrailerFieldsReady()
meth public boolean isUserInRole(java.lang.String)
meth public int getIntHeader(java.lang.String)
meth public jakarta.servlet.http.Cookie[] getCookies()
meth public jakarta.servlet.http.HttpServletMapping getHttpServletMapping()
meth public jakarta.servlet.http.HttpSession getSession()
meth public jakarta.servlet.http.HttpSession getSession(boolean)
meth public jakarta.servlet.http.Part getPart(java.lang.String) throws jakarta.servlet.ServletException,java.io.IOException
meth public jakarta.servlet.http.PushBuilder newPushBuilder()
 anno 0 java.lang.Deprecated(boolean forRemoval=false, java.lang.String since="")
meth public java.lang.String changeSessionId()
meth public java.lang.String getAuthType()
meth public java.lang.String getContextPath()
meth public java.lang.String getHeader(java.lang.String)
meth public java.lang.String getMethod()
meth public java.lang.String getPathInfo()
meth public java.lang.String getPathTranslated()
meth public java.lang.String getQueryString()
meth public java.lang.String getRemoteUser()
meth public java.lang.String getRequestURI()
meth public java.lang.String getRequestedSessionId()
meth public java.lang.String getServletPath()
meth public java.lang.StringBuffer getRequestURL()
meth public java.security.Principal getUserPrincipal()
meth public java.util.Collection<jakarta.servlet.http.Part> getParts() throws jakarta.servlet.ServletException,java.io.IOException
meth public java.util.Enumeration<java.lang.String> getHeaderNames()
meth public java.util.Enumeration<java.lang.String> getHeaders(java.lang.String)
meth public java.util.Map<java.lang.String,java.lang.String> getTrailerFields()
meth public long getDateHeader(java.lang.String)
meth public void login(java.lang.String,java.lang.String) throws jakarta.servlet.ServletException
meth public void logout() throws jakarta.servlet.ServletException
supr jakarta.servlet.ServletRequestWrapper

CLSS public abstract interface jakarta.servlet.http.HttpServletResponse
fld public final static int SC_ACCEPTED = 202
fld public final static int SC_BAD_GATEWAY = 502
fld public final static int SC_BAD_REQUEST = 400
fld public final static int SC_CONFLICT = 409
fld public final static int SC_CONTINUE = 100
fld public final static int SC_CREATED = 201
fld public final static int SC_EXPECTATION_FAILED = 417
fld public final static int SC_FORBIDDEN = 403
fld public final static int SC_FOUND = 302
fld public final static int SC_GATEWAY_TIMEOUT = 504
fld public final static int SC_GONE = 410
fld public final static int SC_HTTP_VERSION_NOT_SUPPORTED = 505
fld public final static int SC_INTERNAL_SERVER_ERROR = 500
fld public final static int SC_LENGTH_REQUIRED = 411
fld public final static int SC_METHOD_NOT_ALLOWED = 405
fld public final static int SC_MISDIRECTED_REQUEST = 421
fld public final static int SC_MOVED_PERMANENTLY = 301
fld public final static int SC_MOVED_TEMPORARILY = 302
fld public final static int SC_MULTIPLE_CHOICES = 300
fld public final static int SC_NON_AUTHORITATIVE_INFORMATION = 203
fld public final static int SC_NOT_ACCEPTABLE = 406
fld public final static int SC_NOT_FOUND = 404
fld public final static int SC_NOT_IMPLEMENTED = 501
fld public final static int SC_NOT_MODIFIED = 304
fld public final static int SC_NO_CONTENT = 204
fld public final static int SC_OK = 200
fld public final static int SC_PARTIAL_CONTENT = 206
fld public final static int SC_PAYMENT_REQUIRED = 402
fld public final static int SC_PERMANENT_REDIRECT = 308
fld public final static int SC_PRECONDITION_FAILED = 412
fld public final static int SC_PROXY_AUTHENTICATION_REQUIRED = 407
fld public final static int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416
fld public final static int SC_REQUEST_ENTITY_TOO_LARGE = 413
fld public final static int SC_REQUEST_TIMEOUT = 408
fld public final static int SC_REQUEST_URI_TOO_LONG = 414
fld public final static int SC_RESET_CONTENT = 205
fld public final static int SC_SEE_OTHER = 303
fld public final static int SC_SERVICE_UNAVAILABLE = 503
fld public final static int SC_SWITCHING_PROTOCOLS = 101
fld public final static int SC_TEMPORARY_REDIRECT = 307
fld public final static int SC_UNAUTHORIZED = 401
fld public final static int SC_UNPROCESSABLE_CONTENT = 422
fld public final static int SC_UNSUPPORTED_MEDIA_TYPE = 415
fld public final static int SC_UPGRADE_REQUIRED = 426
fld public final static int SC_USE_PROXY = 305
intf jakarta.servlet.ServletResponse
meth public abstract boolean containsHeader(java.lang.String)
meth public abstract int getStatus()
meth public abstract java.lang.String encodeRedirectURL(java.lang.String)
meth public abstract java.lang.String encodeURL(java.lang.String)
meth public abstract java.lang.String getHeader(java.lang.String)
meth public abstract java.util.Collection<java.lang.String> getHeaderNames()
meth public abstract java.util.Collection<java.lang.String> getHeaders(java.lang.String)
meth public abstract void addCookie(jakarta.servlet.http.Cookie)
meth public abstract void addDateHeader(java.lang.String,long)
meth public abstract void addHeader(java.lang.String,java.lang.String)
meth public abstract void addIntHeader(java.lang.String,int)
meth public abstract void sendError(int) throws java.io.IOException
meth public abstract void sendError(int,java.lang.String) throws java.io.IOException
meth public abstract void sendRedirect(java.lang.String,int,boolean) throws java.io.IOException
meth public abstract void setDateHeader(java.lang.String,long)
meth public abstract void setHeader(java.lang.String,java.lang.String)
meth public abstract void setIntHeader(java.lang.String,int)
meth public abstract void setStatus(int)
meth public java.util.function.Supplier<java.util.Map<java.lang.String,java.lang.String>> getTrailerFields()
meth public void sendRedirect(java.lang.String) throws java.io.IOException
meth public void sendRedirect(java.lang.String,boolean) throws java.io.IOException
meth public void sendRedirect(java.lang.String,int) throws java.io.IOException
meth public void setTrailerFields(java.util.function.Supplier<java.util.Map<java.lang.String,java.lang.String>>)

CLSS public jakarta.servlet.http.HttpServletResponseWrapper
cons public init(jakarta.servlet.http.HttpServletResponse)
intf jakarta.servlet.http.HttpServletResponse
meth public boolean containsHeader(java.lang.String)
meth public int getStatus()
meth public java.lang.String encodeRedirectURL(java.lang.String)
meth public java.lang.String encodeURL(java.lang.String)
meth public java.lang.String getHeader(java.lang.String)
meth public java.util.Collection<java.lang.String> getHeaderNames()
meth public java.util.Collection<java.lang.String> getHeaders(java.lang.String)
meth public java.util.function.Supplier<java.util.Map<java.lang.String,java.lang.String>> getTrailerFields()
meth public void addCookie(jakarta.servlet.http.Cookie)
meth public void addDateHeader(java.lang.String,long)
meth public void addHeader(java.lang.String,java.lang.String)
meth public void addIntHeader(java.lang.String,int)
meth public void sendError(int) throws java.io.IOException
meth public void sendError(int,java.lang.String) throws java.io.IOException
meth public void sendRedirect(java.lang.String) throws java.io.IOException
meth public void sendRedirect(java.lang.String,boolean) throws java.io.IOException
meth public void sendRedirect(java.lang.String,int) throws java.io.IOException
meth public void sendRedirect(java.lang.String,int,boolean) throws java.io.IOException
meth public void setDateHeader(java.lang.String,long)
meth public void setHeader(java.lang.String,java.lang.String)
meth public void setIntHeader(java.lang.String,int)
meth public void setStatus(int)
meth public void setTrailerFields(java.util.function.Supplier<java.util.Map<java.lang.String,java.lang.String>>)
supr jakarta.servlet.ServletResponseWrapper

CLSS public abstract interface jakarta.servlet.http.HttpSession
innr public abstract interface static Accessor
meth public abstract boolean isNew()
meth public abstract int getMaxInactiveInterval()
meth public abstract jakarta.servlet.ServletContext getServletContext()
meth public abstract java.lang.Object getAttribute(java.lang.String)
meth public abstract java.lang.String getId()
meth public abstract java.util.Enumeration<java.lang.String> getAttributeNames()
meth public abstract long getCreationTime()
meth public abstract long getLastAccessedTime()
meth public abstract void invalidate()
meth public abstract void removeAttribute(java.lang.String)
meth public abstract void setAttribute(java.lang.String,java.lang.Object)
meth public abstract void setMaxInactiveInterval(int)
meth public jakarta.servlet.http.HttpSession$Accessor getAccessor()

CLSS public abstract interface static jakarta.servlet.http.HttpSession$Accessor
 outer jakarta.servlet.http.HttpSession
meth public abstract void access(java.util.function.Consumer<jakarta.servlet.http.HttpSession>)

CLSS public abstract interface jakarta.servlet.http.HttpSessionActivationListener
intf java.util.EventListener
meth public void sessionDidActivate(jakarta.servlet.http.HttpSessionEvent)
meth public void sessionWillPassivate(jakarta.servlet.http.HttpSessionEvent)

CLSS public abstract interface jakarta.servlet.http.HttpSessionAttributeListener
intf java.util.EventListener
meth public void attributeAdded(jakarta.servlet.http.HttpSessionBindingEvent)
meth public void attributeRemoved(jakarta.servlet.http.HttpSessionBindingEvent)
meth public void attributeReplaced(jakarta.servlet.http.HttpSessionBindingEvent)

CLSS public jakarta.servlet.http.HttpSessionBindingEvent
cons public init(jakarta.servlet.http.HttpSession,java.lang.String)
cons public init(jakarta.servlet.http.HttpSession,java.lang.String,java.lang.Object)
meth public java.lang.Object getValue()
meth public java.lang.String getName()
supr jakarta.servlet.http.HttpSessionEvent
hfds name,serialVersionUID,value

CLSS public abstract interface jakarta.servlet.http.HttpSessionBindingListener
intf java.util.EventListener
meth public void valueBound(jakarta.servlet.http.HttpSessionBindingEvent)
meth public void valueUnbound(jakarta.servlet.http.HttpSessionBindingEvent)

CLSS public jakarta.servlet.http.HttpSessionEvent
cons public init(jakarta.servlet.http.HttpSession)
meth public jakarta.servlet.http.HttpSession getSession()
supr java.util.EventObject
hfds serialVersionUID

CLSS public abstract interface jakarta.servlet.http.HttpSessionIdListener
intf java.util.EventListener
meth public abstract void sessionIdChanged(jakarta.servlet.http.HttpSessionEvent,java.lang.String)

CLSS public abstract interface jakarta.servlet.http.HttpSessionListener
intf java.util.EventListener
meth public void sessionCreated(jakarta.servlet.http.HttpSessionEvent)
meth public void sessionDestroyed(jakarta.servlet.http.HttpSessionEvent)

CLSS public abstract interface jakarta.servlet.http.HttpUpgradeHandler
meth public abstract void destroy()
meth public abstract void init(jakarta.servlet.http.WebConnection)

CLSS public final !enum jakarta.servlet.http.MappingMatch
fld public final static jakarta.servlet.http.MappingMatch CONTEXT_ROOT
fld public final static jakarta.servlet.http.MappingMatch DEFAULT
fld public final static jakarta.servlet.http.MappingMatch EXACT
fld public final static jakarta.servlet.http.MappingMatch EXTENSION
fld public final static jakarta.servlet.http.MappingMatch PATH
meth public static jakarta.servlet.http.MappingMatch valueOf(java.lang.String)
meth public static jakarta.servlet.http.MappingMatch[] values()
supr java.lang.Enum<jakarta.servlet.http.MappingMatch>

CLSS public abstract interface jakarta.servlet.http.Part
meth public abstract java.io.InputStream getInputStream() throws java.io.IOException
meth public abstract java.lang.String getContentType()
meth public abstract java.lang.String getHeader(java.lang.String)
meth public abstract java.lang.String getName()
meth public abstract java.lang.String getSubmittedFileName()
meth public abstract java.util.Collection<java.lang.String> getHeaderNames()
meth public abstract java.util.Collection<java.lang.String> getHeaders(java.lang.String)
meth public abstract long getSize()
meth public abstract void delete() throws java.io.IOException
meth public abstract void write(java.lang.String) throws java.io.IOException

CLSS public abstract interface jakarta.servlet.http.PushBuilder
 anno 0 java.lang.Deprecated(boolean forRemoval=false, java.lang.String since="")
meth public abstract jakarta.servlet.http.PushBuilder addHeader(java.lang.String,java.lang.String)
meth public abstract jakarta.servlet.http.PushBuilder method(java.lang.String)
meth public abstract jakarta.servlet.http.PushBuilder path(java.lang.String)
meth public abstract jakarta.servlet.http.PushBuilder queryString(java.lang.String)
meth public abstract jakarta.servlet.http.PushBuilder removeHeader(java.lang.String)
meth public abstract jakarta.servlet.http.PushBuilder sessionId(java.lang.String)
meth public abstract jakarta.servlet.http.PushBuilder setHeader(java.lang.String,java.lang.String)
meth public abstract java.lang.String getHeader(java.lang.String)
meth public abstract java.lang.String getMethod()
meth public abstract java.lang.String getPath()
meth public abstract java.lang.String getQueryString()
meth public abstract java.lang.String getSessionId()
meth public abstract java.util.Set<java.lang.String> getHeaderNames()
meth public abstract void push()

CLSS public abstract interface jakarta.servlet.http.WebConnection
intf java.lang.AutoCloseable
meth public abstract jakarta.servlet.ServletInputStream getInputStream() throws java.io.IOException
meth public abstract jakarta.servlet.ServletOutputStream getOutputStream() throws java.io.IOException

CLSS public abstract interface java.io.Closeable
intf java.lang.AutoCloseable
meth public abstract void close() throws java.io.IOException

CLSS public abstract interface java.io.Flushable
meth public abstract void flush() throws java.io.IOException

CLSS public abstract java.io.InputStream
cons public init()
intf java.io.Closeable
meth public abstract int read() throws java.io.IOException
meth public boolean markSupported()
meth public byte[] readAllBytes() throws java.io.IOException
meth public byte[] readNBytes(int) throws java.io.IOException
meth public int available() throws java.io.IOException
meth public int read(byte[]) throws java.io.IOException
meth public int read(byte[],int,int) throws java.io.IOException
meth public int readNBytes(byte[],int,int) throws java.io.IOException
meth public long skip(long) throws java.io.IOException
meth public long transferTo(java.io.OutputStream) throws java.io.IOException
meth public static java.io.InputStream nullInputStream()
meth public void close() throws java.io.IOException
meth public void mark(int)
meth public void reset() throws java.io.IOException
meth public void skipNBytes(long) throws java.io.IOException
supr java.lang.Object
hfds DEFAULT_BUFFER_SIZE,MAX_BUFFER_SIZE,MAX_SKIP_BUFFER_SIZE

CLSS public abstract java.io.OutputStream
cons public init()
intf java.io.Closeable
intf java.io.Flushable
meth public abstract void write(int) throws java.io.IOException
meth public static java.io.OutputStream nullOutputStream()
meth public void close() throws java.io.IOException
meth public void flush() throws java.io.IOException
meth public void write(byte[]) throws java.io.IOException
meth public void write(byte[],int,int) throws java.io.IOException
supr java.lang.Object

CLSS public abstract interface java.io.Serializable

CLSS public abstract interface java.lang.AutoCloseable
meth public abstract void close() throws java.lang.Exception

CLSS public abstract interface java.lang.Cloneable

CLSS public abstract interface java.lang.Comparable<%0 extends java.lang.Object>
meth public abstract int compareTo({java.lang.Comparable%0})

CLSS public abstract interface !annotation java.lang.Deprecated
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, MODULE, PARAMETER, TYPE])
intf java.lang.annotation.Annotation
meth public abstract !hasdefault boolean forRemoval()
meth public abstract !hasdefault java.lang.String since()

CLSS public abstract java.lang.Enum<%0 extends java.lang.Enum<{java.lang.Enum%0}>>
cons protected init(java.lang.String,int)
innr public final static EnumDesc
intf java.io.Serializable
intf java.lang.Comparable<{java.lang.Enum%0}>
intf java.lang.constant.Constable
meth protected final java.lang.Object clone() throws java.lang.CloneNotSupportedException
meth protected final void finalize()
meth public final boolean equals(java.lang.Object)
meth public final int compareTo({java.lang.Enum%0})
meth public final int hashCode()
meth public final int ordinal()
meth public final java.lang.Class<{java.lang.Enum%0}> getDeclaringClass()
meth public final java.lang.String name()
meth public final java.util.Optional<java.lang.Enum$EnumDesc<{java.lang.Enum%0}>> describeConstable()
meth public java.lang.String toString()
meth public static <%0 extends java.lang.Enum<{%%0}>> {%%0} valueOf(java.lang.Class<{%%0}>,java.lang.String)
supr java.lang.Object
hfds name,ordinal

CLSS public java.lang.Exception
cons protected init(java.lang.String,java.lang.Throwable,boolean,boolean)
cons public init()
cons public init(java.lang.String)
cons public init(java.lang.String,java.lang.Throwable)
cons public init(java.lang.Throwable)
supr java.lang.Throwable
hfds serialVersionUID

CLSS public java.lang.Object
cons public init()
meth protected java.lang.Object clone() throws java.lang.CloneNotSupportedException
meth protected void finalize() throws java.lang.Throwable
 anno 0 java.lang.Deprecated(boolean forRemoval=false, java.lang.String since="9")
meth public boolean equals(java.lang.Object)
meth public final java.lang.Class<?> getClass()
meth public final void notify()
meth public final void notifyAll()
meth public final void wait() throws java.lang.InterruptedException
meth public final void wait(long) throws java.lang.InterruptedException
meth public final void wait(long,int) throws java.lang.InterruptedException
meth public int hashCode()
meth public java.lang.String toString()

CLSS public java.lang.Throwable
cons protected init(java.lang.String,java.lang.Throwable,boolean,boolean)
cons public init()
cons public init(java.lang.String)
cons public init(java.lang.String,java.lang.Throwable)
cons public init(java.lang.Throwable)
intf java.io.Serializable
meth public final java.lang.Throwable[] getSuppressed()
meth public final void addSuppressed(java.lang.Throwable)
meth public java.lang.StackTraceElement[] getStackTrace()
meth public java.lang.String getLocalizedMessage()
meth public java.lang.String getMessage()
meth public java.lang.String toString()
meth public java.lang.Throwable fillInStackTrace()
meth public java.lang.Throwable getCause()
meth public java.lang.Throwable initCause(java.lang.Throwable)
meth public void printStackTrace()
meth public void printStackTrace(java.io.PrintStream)
meth public void printStackTrace(java.io.PrintWriter)
meth public void setStackTrace(java.lang.StackTraceElement[])
supr java.lang.Object
hfds CAUSE_CAPTION,EMPTY_THROWABLE_ARRAY,NULL_CAUSE_MESSAGE,SELF_SUPPRESSION_MESSAGE,SUPPRESSED_CAPTION,SUPPRESSED_SENTINEL,UNASSIGNED_STACK,backtrace,cause,depth,detailMessage,serialVersionUID,stackTrace,suppressedExceptions
hcls PrintStreamOrWriter,SentinelHolder,WrappedPrintStream,WrappedPrintWriter

CLSS public abstract interface java.lang.annotation.Annotation
meth public abstract boolean equals(java.lang.Object)
meth public abstract int hashCode()
meth public abstract java.lang.Class<? extends java.lang.annotation.Annotation> annotationType()
meth public abstract java.lang.String toString()

CLSS public abstract interface !annotation java.lang.annotation.Documented
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[ANNOTATION_TYPE])
intf java.lang.annotation.Annotation

CLSS public abstract interface !annotation java.lang.annotation.Inherited
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[ANNOTATION_TYPE])
intf java.lang.annotation.Annotation

CLSS public abstract interface !annotation java.lang.annotation.Retention
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[ANNOTATION_TYPE])
intf java.lang.annotation.Annotation
meth public abstract java.lang.annotation.RetentionPolicy value()

CLSS public abstract interface !annotation java.lang.annotation.Target
 anno 0 java.lang.annotation.Documented()
 anno 0 java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy value=RUNTIME)
 anno 0 java.lang.annotation.Target(java.lang.annotation.ElementType[] value=[ANNOTATION_TYPE])
intf java.lang.annotation.Annotation
meth public abstract java.lang.annotation.ElementType[] value()

CLSS public abstract interface java.lang.constant.Constable
meth public abstract java.util.Optional<? extends java.lang.constant.ConstantDesc> describeConstable()

CLSS public abstract interface java.util.EventListener

CLSS public java.util.EventObject
cons public init(java.lang.Object)
fld protected java.lang.Object source
intf java.io.Serializable
meth public java.lang.Object getSource()
meth public java.lang.String toString()
supr java.lang.Object
hfds serialVersionUID


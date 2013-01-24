using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using Autofac;
using Autofac.Integration.Mvc;
using Autofac.Integration.WebApi;
using Bootstrap;
using Bootstrap.Autofac;

namespace TU.Web
{
    // Note: For instructions on enabling IIS6 or IIS7 classic mode, 
    // visit http://go.microsoft.com/?LinkId=9394801

    public class MvcApplication : HttpApplication
    {
        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();

            WebApiConfig.Register(GlobalConfiguration.Configuration);
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);
            //AuthConfig.RegisterAuth();

            ConfigureBootstrapper();
        }

        private void ConfigureBootstrapper()
        {
            Bootstrapper
                .IncludingOnly.AssemblyRange(AssemblyLocator.GetAssemblies("TU", true))
                .With.Autofac()
                //.And.EntityFramework()
                .Start();

            var container = Bootstrapper.Container as IContainer;
            if (container != null)
            {
                DependencyResolver.SetResolver(new AutofacDependencyResolver(container));
                var resolver = new AutofacWebApiDependencyResolver(container);
                GlobalConfiguration.Configuration.DependencyResolver = resolver;
            }
        }
    }
}
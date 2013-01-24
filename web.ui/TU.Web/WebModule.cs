using System.Reflection;
using Autofac;
using Autofac.Integration.Mvc;
using Autofac.Integration.WebApi;
using Module = Autofac.Module;

namespace TU.Web
{
    public class WebModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            //builder
            //    .RegisterType<ConfigurationProvider>()
            //    .As<IConfigurationProvider>()
            //    .InstancePerLifetimeScope();

            //builder
            //    .RegisterType<SessionOptionsStore>()
            //    .As<IAppOptionsStore>();

            //builder
            //    .RegisterType<ExceptionPolicy>()
            //    .As<IExceptionPolicy>();

            builder
                .RegisterControllers(Assembly.GetExecutingAssembly());

            builder
                .RegisterApiControllers(Assembly.GetExecutingAssembly());

            //builder
            //    .RegisterApiControllers(Assembly.GetExecutingAssembly());
        }
    }
}
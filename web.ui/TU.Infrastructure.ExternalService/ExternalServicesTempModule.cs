using Autofac;

namespace TU.Infrastructure.ExternalService
{
    public class ExternalServicesTempModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder
                .RegisterType<StubTuWebService>()
                .As<ITuWebService>();
        }
    }
}
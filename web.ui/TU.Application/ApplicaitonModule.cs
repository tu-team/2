using Autofac;

namespace TU.Application
{
    public class ApplicaitonModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder
                .RegisterType<RequestService>()
                .As<IRequestService>()
                .SingleInstance();
        }
    }
}
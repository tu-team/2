using Autofac;

namespace TU.Domain
{
    public class DomainModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder
                .RegisterType<StateManagerService>()
                .As<IStateManagerService>()
                .SingleInstance();
        }
    }
}
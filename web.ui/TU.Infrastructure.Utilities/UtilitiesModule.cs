using Autofac;

namespace TU.Infrastructure.Utilities
{
    public class UtilitiesModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder
                .RegisterType<DateTimeService>()
                .As<IDateTimeService>()
                .SingleInstance();
        }
    }
}
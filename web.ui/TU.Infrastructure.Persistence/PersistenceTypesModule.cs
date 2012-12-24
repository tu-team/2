using Autofac;
using TU.Domain.Queries;
using TU.Infrastructure.Persistence.Queries;

namespace TU.Infrastructure.Persistence
{
    public class PersistenceTypesModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder
                .RegisterType<AutofacQueryBuilder>()
                .As<IQueryBuilder>()
                .InstancePerLifetimeScope();

            builder
                .RegisterType<TuDashboardDbContext>()
                .InstancePerLifetimeScope();

            builder
                .RegisterType<AutofacUnitOfWorkFactory>()
                .As<IUnitOfWorkFactory>()
                .SingleInstance();

            builder
                .RegisterType<AutofacUnitOfWork>()
                .As<IUnitOfWork>();

            //builder.RegisterType<TuDashboardDbContextInitializer>()
            //    .As<IDatabaseInitializer>()
            //    .SingleInstance();

            #region Registering queries

            builder
                .RegisterType<GetAllRequestsQuery>()
                .AsImplementedInterfaces();

            //builder.RegisterType<GetAllTagsQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetAllAuthorsQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetPopularItemsQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetPopularTagsQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetPopularAuthorsQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetItemsByTagIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetItemsByAuthorIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetPopularDepartmentsQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetItemsByDepartmentIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetItemByIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetAllReservationsQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetAllMediumsOnHandQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetUsersBySubstringQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetUserByIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetUserMappingByNameAndProviderIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetUserByNameQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetMediumByIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetReservationsByUserIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetReservationsByLibrarianIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetMediaOnHandByUserIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetMediumInstancesByMediumAndDeptIdQuery>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetVoteByItemAndUserId>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetVotesByItemId>()
            //    .AsImplementedInterfaces();

            //builder.RegisterType<GetItemsByTextQuery>()
            //    .AsImplementedInterfaces();

            #endregion
        }
    }
}
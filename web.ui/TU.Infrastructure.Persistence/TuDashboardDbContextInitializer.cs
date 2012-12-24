using System.Data.Entity;

namespace TU.Infrastructure.Persistence
{
    public interface IDatabaseInitializer : IDatabaseInitializer<TuDashboardDbContext>
    {
    }

    //internal class TuDashboardDbContextInitializer : MigrateDatabaseToLatestVersion<TuDashboardDbContext, Migrations.Configuration>, IDatabaseInitializer
    //{
    //}
}
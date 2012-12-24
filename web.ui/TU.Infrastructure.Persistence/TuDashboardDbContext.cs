using System.Data.Entity;
using TU.Infrastructure.Configuration;

namespace TU.Infrastructure.Persistence
{
    public class TuDashboardDbContext : DbContext
    {
        public TuDashboardDbContext()
            : base("TUDashboard")
        {
        }

        public TuDashboardDbContext(IConfigurationProvider configurationProvider)
            : base(configurationProvider.ConnectionString)
        { }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}
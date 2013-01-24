using System.Collections.Generic;
using TU.Domain.Entities;
using TU.Domain.Queries;
using TU.Domain.Queries.Criterions;

namespace TU.Infrastructure.Persistence.Queries
{
    internal class GetAllRequestsQuery : BaseQuery, IQuery<GetAllCriterion, IEnumerable<Request>>
    {
        public GetAllRequestsQuery(TuDashboardDbContext dbContext)
            : base(dbContext)
        {
        }

        public IEnumerable<Request> Execute(GetAllCriterion criterion)
        {
            return DbContext.Set<Request>();
        }
    }
}
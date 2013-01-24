using System;

namespace TU.Domain.Queries.Criterions
{
    public class GetByIdCriterion : ICriterion
    {
        public Guid Id { get; set; }

        public GetByIdCriterion(Guid id)
        {
            Id = id;
        }
    }
}
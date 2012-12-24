namespace TU.Domain.Queries.Criterions
{
    public class GetAllCriterion : ICriterion
    {
        public static readonly GetAllCriterion Value = new GetAllCriterion();

        private GetAllCriterion()
        { }
    }
}
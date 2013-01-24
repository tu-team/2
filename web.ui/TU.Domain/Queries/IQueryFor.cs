namespace TU.Domain.Queries
{
    public interface IQueryFor<out T>
    {
        T With<TCriterion>(TCriterion criterion) where TCriterion : ICriterion;
    }
}
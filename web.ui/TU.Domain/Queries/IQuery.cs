namespace TU.Domain.Queries
{
    public interface IQuery<in TCriterion, out TResult>
        where TCriterion : ICriterion
    {
        TResult Execute(TCriterion criterion);
    }
}
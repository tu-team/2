namespace TU.Domain.Queries
{
    /// <summary>
    /// Unit of work object factory
    /// </summary>
    public interface IUnitOfWorkFactory
    {
        IUnitOfWork Create();
    }
}
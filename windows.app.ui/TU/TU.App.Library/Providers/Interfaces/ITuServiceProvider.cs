namespace TU.App.Library.Providers.Interfaces
{
    using System.Threading.Tasks;

    using Models;

    public interface ITuServiceProvider
    {
        Task<ResponseModel> SendRequest(RequestModel request);
    }
}
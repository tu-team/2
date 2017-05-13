namespace TU.App.Library.Providers.Interfaces
{
    using System.Threading.Tasks;

    using Models;

    public interface ITuServiceProvider
    {
        /// <summary>
        /// Sends the request to TU service.
        /// </summary>
        /// <param name="request">The request.</param>
        /// <returns></returns>
        Task<ResponseModel> SendRequest(RequestModel request);
    }
}
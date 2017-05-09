namespace TU.App.Library.Providers
{
    using System.Threading.Tasks;

    using Models;
    using Interfaces;

    public class TuServiceProvider : ITuServiceProvider
    {
        #region Implementation of ITuServiceProvider

        public Task<ResponseModel> SendRequest(RequestModel request)
        {
            //TODO
            return null;
        }

        #endregion
    }
}
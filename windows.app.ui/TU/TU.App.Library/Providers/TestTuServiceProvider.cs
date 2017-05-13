namespace TU.App.Library.Providers
{
    using System.Threading.Tasks;

    using Models;
    using Interfaces;

    public class TestTuServiceProvider : ITuServiceProvider
    {
        #region Implementation of ITuServiceProvider

        public async Task<ResponseModel> SendRequest(RequestModel request)
        {
            await Task.Delay(2000);

            return new ResponseModel() {Data = "Emulator response"};
        }

        #endregion
    }
}
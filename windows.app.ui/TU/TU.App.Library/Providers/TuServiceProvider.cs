namespace TU.App.Library.Providers
{
    using System;
    using System.Threading.Tasks;
    using Windows.Storage.Streams;
    using Windows.Web.Http;

    using Models;
    using Interfaces;

    using Microsoft.Toolkit.Uwp;
    using Newtonsoft.Json;

    public class TuServiceProvider : ITuServiceProvider
    {
        #region Private Members

        private readonly SettingsModel _settings;

        #endregion

        public TuServiceProvider(SettingsModel settings)
        {
            _settings = settings;
        }

        #region Implementation of ITuServiceProvider

        public async Task<ResponseModel> SendRequest(RequestModel request)
        {
            try
            {
                using (var httpRequest = new HttpHelperRequest(new Uri(_settings.ServiceAddress), HttpMethod.Post))
                {
                    httpRequest.Content = new HttpStringContent(JsonConvert.SerializeObject(request), UnicodeEncoding.Utf8, "application/json");

                    using (var response = await HttpHelper.Instance.SendRequestAsync(httpRequest))
                    {
                        //TODO 
                        //return JsonConvert.DeserializeObject<ResponseModel>(await response.GetTextResultAsync());

                        return null;
                    }
                }
            }
            catch (Exception exception)
            {
                return new ResponseModel() {Data = exception.Message};
            }
        }

        #endregion
    }
}
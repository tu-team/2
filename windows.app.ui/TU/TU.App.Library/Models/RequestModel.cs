namespace TU.App.Library.Models
{
    using Newtonsoft.Json;

    public class RequestModel
    {
        [JsonProperty("channel")]
        public int Channel { get; set; }

        [JsonProperty("data")]
        public RequestDataModel Data { get; set; }
    }
}
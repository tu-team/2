namespace TU.App.Library.Models
{
    using System;

    using Helpers.Converters;

    using Newtonsoft.Json;

    public class RequestDataModel
    {
        [JsonProperty("time")]
        [JsonConverter(typeof(TimestampConverter))]
        public DateTime Timestamp { get; set; }

        [JsonProperty("data")]
        public string Data { get; set; }
    }
}
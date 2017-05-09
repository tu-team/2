namespace TU.App.Library.Helpers.Converters
{
    using System;
    using System.Globalization;
    using Newtonsoft.Json;

    /// <summary>
    /// Timestamp json converter
    /// </summary>
    /// <seealso cref="Newtonsoft.Json.JsonConverter" />
    public class TimestampConverter : JsonConverter
    {
        #region Overrides of JsonConverter

        /// <summary>
        /// Writes the JSON representation of the object.
        /// </summary>
        /// <param name="writer">The <see cref="T:Newtonsoft.Json.JsonWriter" /> to write to.</param>
        /// <param name="value">The value.</param>
        /// <param name="serializer">The calling serializer.</param>
        public override void WriteJson(JsonWriter writer, object value, JsonSerializer serializer)
        {
            writer.WriteRawValue(((DateTimeOffset)value).ToUnixTimeMilliseconds().ToString(CultureInfo.InvariantCulture));
        }

        /// <summary>
        /// Reads the JSON representation of the object.
        /// </summary>
        /// <param name="reader">The <see cref="T:Newtonsoft.Json.JsonReader" /> to read from.</param>
        /// <param name="objectType">Type of the object.</param>
        /// <param name="existingValue">The existing value of object being read.</param>
        /// <param name="serializer">The calling serializer.</param>
        /// <returns>
        /// The object value.
        /// </returns>
        public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
        {
            if(long.TryParse(reader.Value as string, out long timestamp))
                return DateTimeOffset.FromUnixTimeMilliseconds(timestamp).DateTime;

            return DateTime.MinValue;
        }

        /// <summary>
        /// Determines whether this instance can convert the specified object type.
        /// </summary>
        /// <param name="objectType">Type of the object.</param>
        /// <returns>
        /// <c>true</c> if this instance can convert the specified object type; otherwise, <c>false</c>.
        /// </returns>
        public override bool CanConvert(Type objectType)
        {
            return objectType == typeof(DateTime);
        }

        #endregion
    }
}
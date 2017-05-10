namespace TU.App.Models.Chat
{
    using System;
    using System.Globalization;

    using Interfaces;

    public class ChatMessageModel : IChatMessage
    {
        #region Implementation of IChatMessage

        public virtual UserType User { get; set; }
        public virtual string Message { get; set; }
        public virtual DateTime Time { get; set; }

        public string Status => $"{User}, {Time.ToString("F", CultureInfo.InvariantCulture)}";

        #endregion
    }
}
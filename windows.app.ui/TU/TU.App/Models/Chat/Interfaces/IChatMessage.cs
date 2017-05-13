namespace TU.App.Models.Chat.Interfaces
{
    using System;

    public enum UserType
    {
        User,
        TU
    }

    public interface IChatMessage
    {

        /// <summary>
        /// Gets or sets the user.
        /// </summary>
        /// <value>
        /// The user.
        /// </value>
        UserType User { get; set; }
        
        /// <summary>
        /// Gets or sets the message.
        /// </summary>
        /// <value>
        /// The message.
        /// </value>
        string Message { get; set; }
        
        /// <summary>
        /// Gets or sets the time.
        /// </summary>
        /// <value>
        /// The time.
        /// </value>
        DateTime Time { get; set; }

        /// <summary>
        /// Gets the status.
        /// </summary>
        /// <value>
        /// The status.
        /// </value>
        string Status { get; }
    }
}
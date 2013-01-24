using System;

namespace TU.Application.Dto
{
    public class Message
    {
        public int Order { get; set; }
        public string Text { get; set; }
        public MessageDirection Direction { get; set; }
        public DateTime DateTime { get; set; }
    }
}
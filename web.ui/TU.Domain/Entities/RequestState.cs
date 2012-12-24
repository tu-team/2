namespace TU.Domain.Entities
{
    public enum RequestState
    {
        InProgress = 0,
        AwaitingResponse,
        Error,
        Done
    }
}
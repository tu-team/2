using TU.Domain.Entities;

namespace TU.Domain
{
    public interface IStateManagerService
    {
        RequestState GetRequestState(string requestId);
        void FinalizeRequest(Request request);
    }
}
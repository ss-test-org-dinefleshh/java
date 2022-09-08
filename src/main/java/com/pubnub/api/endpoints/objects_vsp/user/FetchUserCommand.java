package com.pubnub.api.endpoints.objects_vsp.user;

import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.UserId;
import com.pubnub.api.endpoints.objects_api.CompositeParameterEnricher;
import com.pubnub.api.endpoints.objects_api.utils.Include.HavingCustomInclude;
import com.pubnub.api.enums.PNOperationType;
import com.pubnub.api.managers.RetrofitManager;
import com.pubnub.api.managers.TelemetryManager;
import com.pubnub.api.managers.token_manager.TokenManager;
import com.pubnub.api.models.consumer.objects_vsp.user.User;
import com.pubnub.api.models.server.objects_api.EntityEnvelope;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Map;

final class FetchUserCommand extends FetchUser implements HavingCustomInclude<FetchUser> {
    private UserId userId;

    public FetchUserCommand(
            final UserId userId,
            final PubNub pubNub,
            final TelemetryManager telemetryManager,
            final RetrofitManager retrofitManager,
            final TokenManager tokenManager,
            final CompositeParameterEnricher compositeParameterEnricher) {
        super(pubNub, telemetryManager, retrofitManager, tokenManager, compositeParameterEnricher);
        this.userId = userId;
    }

    @Override
    protected Call<EntityEnvelope<User>> executeCommand(Map<String, String> effectiveParams) throws PubNubException {
        return getRetrofit()
                .getUserService()
                .fetchUser(getPubnub().getConfiguration().getSubscribeKey(), userId.getValue(), effectiveParams);
    }

    @Override
    protected User createResponse(Response<EntityEnvelope<User>> input) throws PubNubException {
        if (input.body() != null) {
            return input.body().getData();
        } else {
            return new User();
        }
    }

    @Override
    protected PNOperationType getOperationType() {
        return PNOperationType.PNFetchUserOperation;
    }


    @Override
    public CompositeParameterEnricher getCompositeParameterEnricher() {
        return super.getCompositeParameterEnricher();
    }
}
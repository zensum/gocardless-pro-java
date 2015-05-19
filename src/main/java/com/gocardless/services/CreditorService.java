package com.gocardless.services;

import java.util.List;
import java.util.Map;

import com.gocardless.http.*;
import com.gocardless.resources.Creditor;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

/**
 * Service class for working with creditor resources.
 *
 * Each [payment](https://developer.gocardless.com/pro/2015-04-29/#api-endpoints-payments) taken
 * through the API is linked to a "creditor", to whom the payment is then paid out. In most cases
 * your organisation will have a single "creditor", but the API also supports collecting payments on
 * behalf of others.
 * 
 * Please get in touch if you wish to use this endpoint. Currently, for Anti
 * Money Laundering reasons, any creditors you add must be directly related to your organisation.
 */
public class CreditorService {
    private HttpClient httpClient;

    /**
     * Constructor.  Users of this library should have no need to call this - an instance
     * of this class can be obtained by calling
      {@link com.gocardless.GoCardlessClient#creditors() }.
     */
    public CreditorService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new creditor.
     */
    public CreditorCreateRequest create() {
        return new CreditorCreateRequest(httpClient);
    }

    /**
     * Returns a
     * [cursor-paginated](https://developer.gocardless.com/pro/2015-04-29/#overview-cursor-pagination)
     * list of your creditors.
     */
    public CreditorListRequest<ListResponse<Creditor>> list() {
        return new CreditorListRequest<>(httpClient, ListRequest.<Creditor>pagingExecutor());
    }

    public CreditorListRequest<Iterable<Creditor>> all() {
        return new CreditorListRequest<>(httpClient, ListRequest.<Creditor>iteratingExecutor());
    }

    /**
     * Retrieves the details of an existing creditor.
     */
    public CreditorGetRequest<Creditor> get(String identity) {
        return new CreditorGetRequest<>(httpClient, GetRequest.<Creditor>jsonExecutor(), identity);
    }

    /**
     * Updates a creditor object. Supports all of the fields supported when creating a creditor.
     */
    public CreditorUpdateRequest update(String identity) {
        return new CreditorUpdateRequest(httpClient, identity);
    }

    /**
     * Request class for {@link CreditorService#create }.
     *
     * Creates a new creditor.
     */
    public static final class CreditorCreateRequest extends PostRequest<Creditor> {
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String city;
        private String countryCode;
        private Links links;
        private String name;
        private String postalCode;
        private String region;

        /**
         * The first line of the creditor's address.
         */
        public CreditorCreateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        /**
         * The second line of the creditor's address.
         */
        public CreditorCreateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        /**
         * The third line of the creditor's address.
         */
        public CreditorCreateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        /**
         * The city of the creditor's address.
         */
        public CreditorCreateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code, currently only "GB" is supported.
         */
        public CreditorCreateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CreditorCreateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the logo used on the Redirect Flow payment pages.
         */
        public CreditorCreateRequest withLinksLogo(String logo) {
            if (links == null) {
                links = new Links();
            }
            links.withLogo(logo);
            return this;
        }

        /**
         * The creditor's name.
         */
        public CreditorCreateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * The creditor's postal code.
         */
        public CreditorCreateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * The creditor's address region, county or department.
         */
        public CreditorCreateRequest withRegion(String region) {
            this.region = region;
            return this;
        }

        private CreditorCreateRequest(HttpClient httpClient) {
            super(httpClient);
        }

        @Override
        protected String getPathTemplate() {
            return "/creditors";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected Class<Creditor> getResponseClass() {
            return Creditor.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String logo;

            /**
             * ID of the logo used on the Redirect Flow payment pages.
             */
            public Links withLogo(String logo) {
                this.logo = logo;
                return this;
            }
        }
    }

    /**
     * Request class for {@link CreditorService#list }.
     *
     * Returns a
     * [cursor-paginated](https://developer.gocardless.com/pro/2015-04-29/#overview-cursor-pagination)
     * list of your creditors.
     */
    public static final class CreditorListRequest<S> extends ListRequest<S, Creditor> {
        /**
         * Cursor pointing to the start of the desired set.
         */
        public CreditorListRequest<S> withAfter(String after) {
            setAfter(after);
            return this;
        }

        /**
         * Cursor pointing to the end of the desired set.
         */
        public CreditorListRequest<S> withBefore(String before) {
            setBefore(before);
            return this;
        }

        /**
         * Number of records to return.
         */
        public CreditorListRequest<S> withLimit(Integer limit) {
            setLimit(limit);
            return this;
        }

        private CreditorListRequest(HttpClient httpClient, ListRequestExecutor<S, Creditor> executor) {
            super(httpClient, executor);
        }

        @Override
        protected Map<String, Object> getQueryParams() {
            ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
            params.putAll(super.getQueryParams());
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/creditors";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected TypeToken<List<Creditor>> getTypeToken() {
            return new TypeToken<List<Creditor>>() {};
        }
    }

    /**
     * Request class for {@link CreditorService#get }.
     *
     * Retrieves the details of an existing creditor.
     */
    public static final class CreditorGetRequest<S> extends GetRequest<S, Creditor> {
        @PathParam
        private final String identity;

        private CreditorGetRequest(HttpClient httpClient, GetRequestExecutor<S, Creditor> executor,
                String identity) {
            super(httpClient, executor);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/creditors/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected Class<Creditor> getResponseClass() {
            return Creditor.class;
        }
    }

    /**
     * Request class for {@link CreditorService#update }.
     *
     * Updates a creditor object. Supports all of the fields supported when creating a creditor.
     */
    public static final class CreditorUpdateRequest extends PutRequest<Creditor> {
        @PathParam
        private final String identity;
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String city;
        private String countryCode;
        private Links links;
        private String name;
        private String postalCode;
        private String region;

        /**
         * The first line of the creditor's address.
         */
        public CreditorUpdateRequest withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        /**
         * The second line of the creditor's address.
         */
        public CreditorUpdateRequest withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        /**
         * The third line of the creditor's address.
         */
        public CreditorUpdateRequest withAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
            return this;
        }

        /**
         * The city of the creditor's address.
         */
        public CreditorUpdateRequest withCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * [ISO 3166-1](http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2#Officially_assigned_code_elements)
         * alpha-2 code, currently only "GB" is supported.
         */
        public CreditorUpdateRequest withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CreditorUpdateRequest withLinks(Links links) {
            this.links = links;
            return this;
        }

        /**
         * ID of the [bank
         * account](https://developer.gocardless.com/pro/2015-04-29/#api-endpoints-creditor-bank-accounts)
         * which is set up to receive payouts in EUR.
         */
        public CreditorUpdateRequest withLinksDefaultEurPayoutAccount(String defaultEurPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultEurPayoutAccount(defaultEurPayoutAccount);
            return this;
        }

        /**
         * ID of the [bank
         * account](https://developer.gocardless.com/pro/2015-04-29/#api-endpoints-creditor-bank-accounts)
         * which is set up to receive payouts in GBP.
         */
        public CreditorUpdateRequest withLinksDefaultGbpPayoutAccount(String defaultGbpPayoutAccount) {
            if (links == null) {
                links = new Links();
            }
            links.withDefaultGbpPayoutAccount(defaultGbpPayoutAccount);
            return this;
        }

        /**
         * ID of the logo used on the Redirect Flow payment pages.
         */
        public CreditorUpdateRequest withLinksLogo(String logo) {
            if (links == null) {
                links = new Links();
            }
            links.withLogo(logo);
            return this;
        }

        /**
         * The creditor's name.
         */
        public CreditorUpdateRequest withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * The creditor's postal code.
         */
        public CreditorUpdateRequest withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * The creditor's address region, county or department.
         */
        public CreditorUpdateRequest withRegion(String region) {
            this.region = region;
            return this;
        }

        private CreditorUpdateRequest(HttpClient httpClient, String identity) {
            super(httpClient);
            this.identity = identity;
        }

        @Override
        protected Map<String, String> getPathParams() {
            ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
            params.put("identity", identity);
            return params.build();
        }

        @Override
        protected String getPathTemplate() {
            return "/creditors/:identity";
        }

        @Override
        protected String getEnvelope() {
            return "creditors";
        }

        @Override
        protected Class<Creditor> getResponseClass() {
            return Creditor.class;
        }

        @Override
        protected boolean hasBody() {
            return true;
        }

        public static class Links {
            private String defaultEurPayoutAccount;
            private String defaultGbpPayoutAccount;
            private String logo;

            /**
             * ID of the [bank
             * account](https://developer.gocardless.com/pro/2015-04-29/#api-endpoints-creditor-bank-accounts)
             * which is set up to receive payouts in EUR.
             */
            public Links withDefaultEurPayoutAccount(String defaultEurPayoutAccount) {
                this.defaultEurPayoutAccount = defaultEurPayoutAccount;
                return this;
            }

            /**
             * ID of the [bank
             * account](https://developer.gocardless.com/pro/2015-04-29/#api-endpoints-creditor-bank-accounts)
             * which is set up to receive payouts in GBP.
             */
            public Links withDefaultGbpPayoutAccount(String defaultGbpPayoutAccount) {
                this.defaultGbpPayoutAccount = defaultGbpPayoutAccount;
                return this;
            }

            /**
             * ID of the logo used on the Redirect Flow payment pages.
             */
            public Links withLogo(String logo) {
                this.logo = logo;
                return this;
            }
        }
    }
}
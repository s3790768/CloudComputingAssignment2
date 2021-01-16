package cloudcomputing.utils

import com.stripe.Stripe

class Stripe {

    companion object {
        fun init(){
            Stripe.apiKey = "sk_test_51IAAvPEhoYdXR4dIfuqNWdbv7V2GULRAlSmziXFAlj2ELbDcuPXiwejawkFnqDCebENZOpSbVFeUWBghS0qpBxSW00aj0ZZNVN"
        }
    }
}
import samltest.UserAcctPasswordEncoderListener
// Place your Spring DSL code here
beans = {
    userAcctPasswordEncoderListener(UserAcctPasswordEncoderListener, ref('hibernateDatastore'))
}

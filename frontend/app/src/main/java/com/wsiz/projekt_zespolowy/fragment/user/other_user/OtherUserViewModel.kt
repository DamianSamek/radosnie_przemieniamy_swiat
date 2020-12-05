package com.wsiz.projekt_zespolowy.fragment.user.other_user

import androidx.hilt.lifecycle.ViewModelInject
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import com.wsiz.projekt_zespolowy.fragment.user.UserViewModel

class OtherUserViewModel @ViewModelInject constructor(userPostRepository: PostRepository) : UserViewModel(userPostRepository)
package com.blumenstreetdoo.nora_pub.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blumenstreetdoo.nora_pub.R
import com.blumenstreetdoo.nora_pub.domain.models.BeerDetails
import com.blumenstreetdoo.nora_pub.domain.models.FavoriteBeer
import com.blumenstreetdoo.nora_pub.ui.theme.NoraColors
import com.blumenstreetdoo.nora_pub.ui.theme.NoraTypography
import com.blumenstreetdoo.nora_pub.utils.copyUriToInternalFile
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class ProfileFragment : Fragment() {
    private val favoriteViewModel: FavoriteViewModel by activityViewModel<FavoriteViewModel>()
    private val profileViewModel: ProfileViewModel by viewModel()

    // launcher for notification permission
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        profileViewModel.onPermissionResult(isGranted)
        if (!isGranted) {
            Snackbar.make(
                requireView(),
                getString(R.string.notifications_permission_denied),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    // launcher for picking from gallery
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { pickedUri ->
            // copy into internal storage
            val internalUri = requireContext().copyUriToInternalFile(pickedUri)
            // save local Uri in DataStore
            profileViewModel.savePhotoPath(internalUri.toString())
        }
    }

    // launcher for taking picture
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = createImageUri()
            currentPhotoUri = uri
            cameraLauncher.launch(uri)
        } else {
            Snackbar.make(
                requireView(),
                getString(R.string.camera_permission_denied),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            currentPhotoUri?.let { profileViewModel.savePhotoPath(it.toString()) }
        }
    }

    private var currentPhotoUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme(
                    colorScheme = NoraColors,
                    typography = NoraTypography,
                ) {
                    ProfileScreen(
                        profileViewModel = profileViewModel,
                        favoriteViewModel = favoriteViewModel,
                        onItemClick = ::onFavBeerClick,
                        onIconFavoriteClick = ::onIconFavoriteClick,
                        onPickFromGallery = { galleryLauncher.launch("image/*") },
                        onTakePhoto = {
                            if (hasCameraPermission()) {
                                // generate a new uri and launch camera
                                val uri = createImageUri()
                                currentPhotoUri = uri
                                cameraLauncher.launch(uri)
                            } else {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init notification-permission state
        profileViewModel.onPermissionResult(hasNotificationPermission())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun onFavBeerClick(beerDetails: BeerDetails) {
        val action =
            ProfileFragmentDirections.actionNavigationFavoriteToBeerDetailsFragment(beerDetails.id)
        findNavController().navigate(action)
    }

    private fun onIconFavoriteClick(favBeer: FavoriteBeer) {
        favoriteViewModel.toggleFavorite(favBeer)
    }

    // Creates a content:// URI via FileProvider
    private fun createImageUri(): Uri {
        val file = File(requireContext().externalCacheDir, "images").apply {
            mkdirs()
        }
        val imageFile = File(file, "avatar_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            imageFile
        )
    }

}

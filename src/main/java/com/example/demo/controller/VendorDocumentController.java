@RestController
@RequestMapping("/api/vendor-documents")
@Tag(name = "Vendor Documents")
public class VendorDocumentController {

    private final VendorDocumentService service;

    public VendorDocumentController(VendorDocumentService service) {
        this.service = service;
    }

    @PostMapping("/{vendorId}/{typeId}")
    public VendorDocument upload(@PathVariable Long vendorId,
                                 @PathVariable Long typeId,
                                 @RequestBody VendorDocument doc) {
        return service.uploadDocument(vendorId, typeId, doc);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<VendorDocument> getVendorDocs(@PathVariable Long vendorId) {
        return service.getDocumentsForVendor(vendorId);
    }

    @GetMapping("/{id}")
    public VendorDocument get(@PathVariable Long id) {
        return service.getDocument(id);
    }
}
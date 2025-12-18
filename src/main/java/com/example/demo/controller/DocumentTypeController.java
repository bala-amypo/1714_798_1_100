@RestController
@RequestMapping("/api/document-types")
@Tag(name = "Document Types")
public class DocumentTypeController {

    private final DocumentTypeService service;

    public DocumentTypeController(DocumentTypeService service) {
        this.service = service;
    }

    @PostMapping
    public DocumentType create(@RequestBody DocumentType d) {
        return service.createDocumentType(d);
    }

    @GetMapping
    public List<DocumentType> getAll() {
        return service.getAllDocumentTypes();
    }

    @GetMapping("/{id}")
    public DocumentType get(@PathVariable Long id) {
        return service.getDocumentType(id);
    }
}
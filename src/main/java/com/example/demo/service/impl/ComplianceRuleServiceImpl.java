@Service
public class ComplianceRuleServiceImpl implements ComplianceRuleService {

    private final ComplianceRuleRepository repository;

    public ComplianceRuleServiceImpl(ComplianceRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ComplianceRule createRule(ComplianceRule rule) {
        return repository.save(rule);
    }

    @Override
    public List<ComplianceRule> getAllRules() {
        return repository.findAll();
    }

    @Override
    public ComplianceRule findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
    }
}

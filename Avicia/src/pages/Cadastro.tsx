// Pagina Cadastro version final 2
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useToast } from "@/hooks/use-toast";
import { User, Mail, Lock, Stethoscope, Phone, Calendar, MapPin, Briefcase, Building, ArrowLeft } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const Cadastro = () => {
  const { toast } = useToast();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    cpf: '',
    telefone: '',
    endereco: '',
    dataNascimento: '',
    sexo: '',
    estadoCivil: '',
    profissao: ''
  });
  const [loading, setLoading] = useState(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormData(prev => ({ ...prev, [id]: value }));
  };

  const handleSelectChange = (field: string, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (formData.password !== formData.confirmPassword) {
      toast({
        title: "Erro",
        description: "As senhas não coincidem",
        variant: "destructive",
      });
      return;
    }

    setLoading(true);
    try {
      console.log('📋 Dados do form:', formData);
      
      const role = await api.getRoleByName('PACIENTE');
      const idRole = role.idRole;
      console.log('🔍 Role PACIENTE encontrada:', { idRole });

      const nomeCompleto = formData.name.trim();
      const ultimoEspaco = nomeCompleto.lastIndexOf(' ');
      const nome = ultimoEspaco > 0 ? nomeCompleto.substring(0, ultimoEspaco) : nomeCompleto;
      const sobrenome = ultimoEspaco > 0 ? nomeCompleto.substring(ultimoEspaco + 1) : '';

      const usuarioData = {
        nome,
        sobrenome,
        cpf: formData.cpf.replace(/\D/g, ''),
        email: formData.email,
        senha: formData.password,
        telefone: formData.telefone,
        ativo: true,
        mfaHabilitado: false,
        dataCriacao: new Date().toISOString().split('T')[0],
        idRole: idRole
      };
      
      const usuarioCriado = await api.criarUsuario(usuarioData);
      const idUsuario = usuarioCriado.idUsuario;
      console.log('👤 Usuário criado:', { idUsuario });

      const pacienteData = {
        idUsuario: idUsuario,
        dataNascimento: formData.dataNascimento,
        sexo: formData.sexo === 'M' ? 'MASCULINO' : formData.sexo === 'F' ? 'FEMININO' : 'OUTRO',
        estadoCivil: formData.estadoCivil,
        profissao: formData.profissao,
        endereco: formData.endereco,
        preferenciaContato: 'EMAIL'
      };
      
      const pacienteCriado = await api.criarPaciente(pacienteData);
      console.log('🏥 Paciente criado:', { idPaciente: pacienteCriado.idPaciente });

      toast({
        title: "Cadastro realizado com sucesso!",
        description: `Bem-vindo, ${usuarioCriado.nome}! Seu ID é ${pacienteCriado.idPaciente}`,
      });
      navigate("/login");
      
    } catch (error: any) {
      console.error('❌ Erro detalhado:', error);
      toast({
        title: "Erro no cadastro",
        description: error.message || "Tente novamente mais tarde",
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-medical-light via-background to-medical-secondary flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        <div className="text-center mb-8">
          <div className="flex items-center justify-center mb-4">
            <div className="bg-primary rounded-full p-3">
              <Stethoscope className="h-8 w-8 text-primary-foreground" />
            </div>
          </div>
          <h1 className="text-4xl font-bold bg-gradient-to-r from-primary to-info bg-clip-text text-transparent">
            AVICIA
          </h1>
          <p className="text-muted-foreground mt-2">
            Criar Nova Conta
          </p>
        </div>

        <Card className="shadow-[var(--medical-glow)] border-medical-secondary/30">
          <CardHeader className="text-center">
            <CardTitle className="text-2xl text-primary flex items-center justify-center gap-2">
              <Button
                variant="ghost"
                size="icon"
                onClick={() => navigate("/login")}
                className="absolute left-4"
              >
                <ArrowLeft className="h-4 w-4" />
              </Button>
              Criar Conta
            </CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="name">Nome Completo</Label>
                <div className="relative">
                  <User className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Input
                    id="name"
                    placeholder="Seu nome completo"
                    className="pl-10"
                    required
                    value={formData.name}
                    onChange={handleInputChange}
                  />
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="email">E-mail</Label>
                <div className="relative">
                  <Mail className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Input
                    id="email"
                    type="email"
                    placeholder="seu@email.com"
                    className="pl-10"
                    required
                    value={formData.email}
                    onChange={handleInputChange}
                  />
                </div>
              </div>

              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="password">Senha</Label>
                  <div className="relative">
                    <Lock className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                    <Input
                      id="password"
                      type="password"
                      placeholder="Sua senha"
                      className="pl-10"
                      required
                      value={formData.password}
                      onChange={handleInputChange}
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="confirmPassword">Confirmar Senha</Label>
                  <div className="relative">
                    <Lock className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                    <Input
                      id="confirmPassword"
                      type="password"
                      placeholder="Confirme sua senha"
                      className="pl-10"
                      required
                      value={formData.confirmPassword}
                      onChange={handleInputChange}
                    />
                  </div>
                </div>
              </div>

              <div className="space-y-4 pt-4 border-t border-border">
                <div className="grid grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="cpf">CPF</Label>
                    <Input
                      id="cpf"
                      placeholder="000.000.000-00"
                      required
                      value={formData.cpf}
                      onChange={handleInputChange}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="telefone">Telefone</Label>
                    <div className="relative">
                      <Phone className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                      <Input
                        id="telefone"
                        placeholder="(00) 00000-0000"
                        className="pl-10"
                        required
                        value={formData.telefone}
                        onChange={handleInputChange}
                      />
                    </div>
                  </div>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="endereco">Endereço</Label>
                  <div className="relative">
                    <MapPin className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                    <Input
                      id="endereco"
                      placeholder="Seu endereço completo"
                      className="pl-10"
                      required
                      value={formData.endereco}
                      onChange={handleInputChange}
                    />
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="dataNascimento">Data de Nascimento</Label>
                    <div className="relative">
                      <Calendar className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                      <Input
                        id="dataNascimento"
                        type="date"
                        className="pl-10"
                        required
                        value={formData.dataNascimento}
                        onChange={handleInputChange}
                      />
                    </div>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="sexo">Sexo</Label>
                    <Select 
                      value={formData.sexo}
                      onValueChange={(value) => handleSelectChange('sexo', value)}
                      required
                    >
                      <SelectTrigger>
                        <SelectValue placeholder="Selecione" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="M">Masculino</SelectItem>
                        <SelectItem value="F">Feminino</SelectItem>
                        <SelectItem value="Outro">Outro</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="estadoCivil">Estado Civil</Label>
                    <Select 
                      value={formData.estadoCivil}
                      onValueChange={(value) => handleSelectChange('estadoCivil', value)}
                      required
                    >
                      <SelectTrigger>
                        <SelectValue placeholder="Selecione" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="solteiro">Solteiro(a)</SelectItem>
                        <SelectItem value="casado">Casado(a)</SelectItem>
                        <SelectItem value="divorciado">Divorciado(a)</SelectItem>
                        <SelectItem value="viuvo">Viúvo(a)</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="profissao">Profissão</Label>
                    <div className="relative">
                      <Briefcase className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                      <Input
                        id="profissao"
                        placeholder="Sua profissão"
                        className="pl-10"
                        required
                        value={formData.profissao}
                        onChange={handleInputChange}
                      />
                    </div>
                  </div>
                </div>
              </div>

              <Button 
                type="submit" 
                disabled={loading}
                className="w-full bg-gradient-to-r from-primary to-info hover:from-primary/90 hover:to-info/90 transition-all duration-300"
              >
                {loading ? "Criando..." : "Criar Conta"} 
              </Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Cadastro;
